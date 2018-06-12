package services;

import communication.handlers.VehicleTrackingHandler;
import dao.TranslocationDao;
import dao.VehicleDao;
import dto.*;
import entities.Translocation;
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

	@Inject
	TrackingService trackingService;

	@Inject
	private VehicleTrackingHandler vehicleTrackingHandler;

	//The amount of minutes that need to have passed since the previous Translocation,
	//to consider the next translocation the start of a new journey.
	private final int minuteThreshold = 20;

	//The distance in meters the previous translocation can be away before it is flagged.
	//If the translocations are send every minute, this translates to 240 km/h
	private final double flagDistance = 4000;

	@Override
	public void createTranslocation(TranslocationDto translocationDto) throws VehicleException {
			Translocation translocation = new Translocation(
					vehicleDao.getVehicleBySerialNumber(translocationDto.getSerialNumber()),
					translocationDto.getLatitude(),
					translocationDto.getLongitude(),
					LocalDateTimeParser.stringToLocalDateTime(translocationDto.getTimestamp()),
					translocationDto.getCountryCode(),
					shouldTranslocationbeFlagged(translocationDto.getLatitude(),
							translocationDto.getLongitude(),
							translocationDto.getSerialNumber()));

			translocationDao.createTranslocation(translocation);

			String licensePlate = vehicleDao.getVehicleBySerialNumber(translocationDto.getSerialNumber()).getLicensePlate();

			if (trackingService.findTrackings(licensePlate)) {
				sendTrackingsToPolice(licensePlate, translocation);
			}
	}

	private void sendTrackingsToPolice(String licensePlate, Translocation translocation){
		try {
			InternalTranslocationDto internalTranslocationDto = new InternalTranslocationDto(translocation);
			TrackingInfoDto trackingInfoDto = new TrackingInfoDto(licensePlate, internalTranslocationDto);
			this.vehicleTrackingHandler.publishTracking(trackingInfoDto);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public AdministrationDto getAdministrationDto(long vehicleId, LocalDateTime startDate, LocalDateTime endDate){
			List<Translocation> translocations = translocationDao.getTranslocations(vehicleId, startDate, endDate);
			List<InternalTranslocationDto> internalTranslocationDtos = convertToInternalTranslocationDto(translocations);
			return divideTranslocationsIntoJourneys(internalTranslocationDtos);
	}

	public AdministrationDto getAdministrationDto(String licensePlate, LocalDateTime startDate, LocalDateTime endDate){
		List<Translocation> translocations = translocationDao.getTranslocations(licensePlate, startDate, endDate);
		List<InternalTranslocationDto> internalTranslocationDtos = convertToInternalTranslocationDto(translocations);
		return divideTranslocationsIntoJourneys(internalTranslocationDtos);
	}

	/**
	 * Divides a list of translocations into Journeys.
	 * This method uses the isTranslocationJourneyStart function to decide if a translocation is the start of a new JourneyDto.
	 * NOTE: This method also converts your translocations into translocationDto Objects.
	 * @param internalTranslocationDtos
	 * @return an AdinistrationDto object that contains a list of Journeys.
	 */
	private AdministrationDto divideTranslocationsIntoJourneys(List<InternalTranslocationDto> internalTranslocationDtos){

		List<JourneyDto> journeys = new ArrayList<>();

		if (internalTranslocationDtos == null || internalTranslocationDtos.size() == 0) {
			return new AdministrationDto(journeys);
		}

		//Initialize the first journey
		int currentJourney = 0;
		JourneyDto initialJourney = new JourneyDto();
		journeys.add(initialJourney);

		//List<InternalTranslocationDto> internalTranslocationDtos = convertToInternalTranslocationDto(translocations);

		//Set first journey as previous journey.
		InternalTranslocationDto previousTranslocationDto = internalTranslocationDtos.get(0);

		for (InternalTranslocationDto internalTranslocationDto : internalTranslocationDtos){
			if (isTranslocationJourneyStart(internalTranslocationDto, previousTranslocationDto)){
				//New JourneyDto
				JourneyDto journeyDto = new JourneyDto();
				journeyDto.addTranslocation(internalTranslocationDto);
				journeys.add(journeyDto);
				currentJourney++;
			}
			else{
				//Current journey
				journeys.get(currentJourney).addTranslocation(internalTranslocationDto);
			}
			previousTranslocationDto = internalTranslocationDto;
		}

		return new AdministrationDto(journeys);
	}

	/**
	 * Converts a list of translocations to a list of translocationDto objects.
	 * @param translocations
	 * @return
	 */
	public List<InternalTranslocationDto> convertToInternalTranslocationDto(List<Translocation> translocations){

		List<InternalTranslocationDto> internalTranslocationDtos = new ArrayList<>();

		for (Translocation translocation : translocations){
			InternalTranslocationDto internalTranslocationDto = new InternalTranslocationDto(translocation);
			internalTranslocationDtos.add(internalTranslocationDto);
		}

		return internalTranslocationDtos;
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
	 * @param serialNumber
	 * @return
	 */
	private boolean shouldTranslocationbeFlagged(double lat, double lon, String serialNumber){
		Translocation previousTranslocation = translocationDao.getLatestTranslocationBySerialNumber(serialNumber);

		//Maybe it's the first translocation ever.
		if (previousTranslocation == null){
			return false;
		}

		double distance = calculateDistance(lat, previousTranslocation.getLat(), lon, previousTranslocation.getLon(), 0, 0);

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

		return Math.sqrt(distance);
	}


}
