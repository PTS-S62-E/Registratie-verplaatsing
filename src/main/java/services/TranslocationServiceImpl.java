package services;

import com.pts62.common.europe.facades.ITranslocationFacade;
import dao.TranslocationDao;
import dao.VehicleDao;
import dto.AdministrationDto;
import dto.CreateTranslocationDto;
import dto.TranslocationDto;
import entities.Translocation;
import dto.JourneyDto;
import exceptions.VehicleException;
import util.LocalDateTimeParser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TranslocationServiceImpl implements TranslocationService{

	@Inject
	TranslocationDao translocationDao;

	@Inject
	VehicleDao vehicleDao;

	//The amount of minutes that need to have passed since the previous Translocation,
	//to consider the next translocation the start of a new journey.
	private final int minuteThreshold = 20;

	//The distance in meters the previous translocation can be away before it is flagged.
	//If the translocations are send every minute, this translates to 240 km/h
	private final double flagDistance = 4000;

	public List<Translocation> getTranslocations(long vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
		return translocationDao.getTranslocations(vehicleId, startDate, endDate);
	}

	@Override
	public TranslocationDto getTranslocation(long id) {
		return new TranslocationDto(translocationDao.getTranslocation(id));
	}

	@Override
	public void createTranslocation(CreateTranslocationDto createTranslocationDto) throws VehicleException {
		Translocation translocation = new Translocation(
				vehicleDao.getVehicle(createTranslocationDto.getVehicleId()),
				createTranslocationDto.getLatitude(),
				createTranslocationDto.getLongitude(),
				LocalDateTimeParser.stringToLocalDateTime(createTranslocationDto.getTimestamp()),
				createTranslocationDto.getCountryCode(),
				shouldTranslocationbeFlagged(createTranslocationDto.getLatitude(),
						createTranslocationDto.getLongitude(),
						createTranslocationDto.getVehicleId()));

		translocationDao.createTranslocation(translocation);
	}

	public AdministrationDto getAdministrationDto(long vehicleId, LocalDateTime startDate, LocalDateTime endDate){
			List<Translocation> translocations = translocationDao.getTranslocations(vehicleId, startDate, endDate);
			return divideTranslocationsIntoJourneys(translocations);
	}

	/**
	 * Divides a list of translocations into Journeys.
	 * This method uses the isTranslocationJourneyStart function to decide if a translocation is the start of a new JourneyDto.
	 * NOTE: This method also converts your translocations into translocationDto Objects.
	 * @param translocations
	 * @return an AdinistrationDto object that contains a list of Journeys.
	 */
	private AdministrationDto divideTranslocationsIntoJourneys(List<Translocation> translocations){

		List<JourneyDto> journeys = new ArrayList<>();

		if (translocations == null || translocations.size() == 0) {
			return new AdministrationDto(journeys);
		}

		//Initialize the first journey
		int currentJourney = 0;
		JourneyDto initialJourney = new JourneyDto();
		journeys.add(initialJourney);

		List<TranslocationDto> translocationDtos = convertTranslocationToDto(translocations);

		//Set first journey as previous journey.
		TranslocationDto previousTranslocationDto = translocationDtos.get(0);

		for (TranslocationDto translocationDto : translocationDtos){
			if (isTranslocationJourneyStart(translocationDto, previousTranslocationDto)){
				//New JourneyDto
				JourneyDto journeyDto = new JourneyDto();
				journeyDto.addTranslocation(translocationDto);
				journeys.add(journeyDto);
				currentJourney++;
			}
			else{
				//Current journey
				journeys.get(currentJourney).addTranslocation(translocationDto);
			}
			previousTranslocationDto = translocationDto;
		}

		return new AdministrationDto(journeys);
	}

	/**
	 * Converts a list of translocations to a list of translocationDto objects.
	 * @param translocations
	 * @return
	 */
	private List<TranslocationDto> convertTranslocationToDto(List<Translocation> translocations){
		List<TranslocationDto> translocationDtos = new ArrayList<>();

		for (Translocation translocation : translocations){
			TranslocationDto translocationDto = new TranslocationDto(translocation);
			translocationDtos.add(translocationDto);
		}

		return translocationDtos;
	}

	/**
	 * Checks if the given translocation is more that #minuteTreshold minutes away from the previous translocation.
	 * minuteTreshold is defined as an int at the top of this class.
	 * @param translocationDto
	 * @param previousTranslocationDto
	 * @return TRUE if the the given translocation is more that #minuteTreshold minutes away from the previous translocation.
	 */
	private boolean isTranslocationJourneyStart(TranslocationDto translocationDto, TranslocationDto previousTranslocationDto){
		LocalDateTime previous =  LocalDateTimeParser.stringToLocalDateTime(previousTranslocationDto.getTimestamp());
		LocalDateTime now = LocalDateTimeParser.stringToLocalDateTime(translocationDto.getTimestamp());
		if(Duration.between(previous, now).toMinutes() < minuteThreshold){
			return false;
		}
		else return true;
	}

	/**
	 * Check if the distance between the last translocation and the current translocation is less than the flagDistance.
	 * Flagdistance is defined as an int at the top of this class.
	 * @param lat
	 * @param lon
	 * @param id
	 * @return
	 */
	private boolean shouldTranslocationbeFlagged(double lat, double lon, long id){
		Translocation previousTranslocation = translocationDao.getLatestTranslocationByVehicleId(id);
		double distance = calculateDistance(lat, previousTranslocation.getLat(), lon, previousTranslocation.getLon(), 0, 0);
		System.out.println("Long, Lat, Id: " + Double.toString(lon) + ", " + Double.toString(lat) + ", " + Double.toString(id));
		System.out.println("Prev Long, Lat, Id: " +
				Double.toString(previousTranslocation.getLongitude()) + ", " +
				Double.toString(previousTranslocation.getLatitude()) + ", " +
				Double.toString(previousTranslocation.getId()));

		if(distance > flagDistance){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 *
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	private double calculateDistance(double lat1, double lat2, double lon1,
								  double lon2, double el1, double el2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		System.out.println("DISTANCE: " + Double.toString(Math.sqrt(distance)));
		return Math.sqrt(distance);
	}


}
