package services;

import com.pts62.common.europe.facades.ITranslocationFacade;
import dao.TranslocationDao;
import dao.VehicleDao;
import dto.AdministrationDto;
import dto.CreateTranslocationDto;
import dto.TranslocationDto;
import entities.Translocation;
import dto.JourneyDto;
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

	public List<Translocation> getTranslocations(long vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
		return translocationDao.getTranslocations(vehicleId, startDate, endDate);
	}

	@Override
	public Translocation getTranslocation(long id) {
		return translocationDao.getTranslocation(id);
	}

	@Override
	public void createTranslocation(CreateTranslocationDto createTranslocationDto) {
		/*
		Translocation translocation = new Translocation(
				vehicleDao.getVehicle(createTranslocationDto.getVehicleId()),
				createTranslocationDto.getLatitude(),
				createTranslocationDto.getLongitude(),
				LocalDateTimeParser.stringToLocalDateTime(createTranslocationDto.getTimestamp()),
				createTranslocationDto.getCountryCode());

		translocationDao.createTranslocation(translocation);
		*/
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
}
