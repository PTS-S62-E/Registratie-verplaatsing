package services;

import dao.TranslocationDao;
import dao.VehicleDao;
import dto.AdministrationDto;
import dto.TranslocationDto;
import entities.Translocation;
import model.Journey;
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

	@Override
	public List<Translocation> getTranslocations(long vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
		return translocationDao.getTranslocations(vehicleId, startDate, endDate);
	}

	@Override
	public Translocation getTranslocation(long id) {
		return translocationDao.getTranslocation(id);
	}

	@Override
	public void createTranslocation(TranslocationDto translocationDto) {
		Translocation translocation = new Translocation(
				vehicleDao.getVehicle(translocationDto.getVehicleId()),
				translocationDto.getLatitude(),
				translocationDto.getLongitude(),
				LocalDateTimeParser.stringToLocalDateTime(translocationDto.getTimestamp()),
				translocationDto.getCountryCode());

		translocationDao.createTranslocation(translocation);
	}

	public AdministrationDto getAdministrationDto(long vehicleId, LocalDateTime startDate, LocalDateTime endDate){
			List<Translocation> translocations = translocationDao.getTranslocations(vehicleId, startDate, endDate);
			return divideTranslocationsIntoJourneys(translocations);
	}

	/**
	 * Divides a list of translocations into Journeys.
	 * This method uses the isTranslocationJourneyStart function to decide if a translocation is the start of a new Journey.
	 * @param translocations
	 * @return an AdinistrationDto object that contains a list of Journeys.
	 */
	private AdministrationDto divideTranslocationsIntoJourneys(List<Translocation> translocations){

		//Set first journey as previous journey.
		Translocation previousTranslocation = translocations.get(0);

		List<Journey> journeys = new ArrayList<>();

		//Initialize the first journey
		int currentJourney = 0;
		Journey initialJourney = new Journey();
		journeys.add(initialJourney);

		for (Translocation translocation : translocations){
			if (isTranslocationJourneyStart(translocation, previousTranslocation)){
				//New Journey
				Journey journey = new Journey();
				journey.addTranslocation(translocation);
				journeys.add(journey);
				currentJourney++;
			}
			else{
				//Current journey
				journeys.get(currentJourney).addTranslocation(translocation);
			}
			previousTranslocation = translocation;
		}

		return new AdministrationDto(journeys);
	}

	/**
	 * Checks if the given translocation is more that #minuteTreshold minutes away from the previous translocation.
	 * minuteTreshold is defined as an int at the top of this class.
	 * @param translocation
	 * @param previousTranslocation
	 * @return TRUE if the the given translocation is more that #minuteTreshold minutes away from the previous translocation.
	 */
	private boolean isTranslocationJourneyStart(Translocation translocation, Translocation previousTranslocation){
		if(Duration.between(previousTranslocation.getTimestamp(), translocation.getTimestamp()).toMinutes() < minuteThreshold){
			return false;
		}
		else return true;
	}
}
