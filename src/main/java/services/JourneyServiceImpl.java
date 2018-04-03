package services;

import dao.TranslocationDao;
import model.Journey;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

public class JourneyServiceImpl implements JourneyService {

	@Inject
	TranslocationDao translocationDao;

	@Override
	public List<Journey> getJourneys(String licensePlate, LocalDateTime startDate, LocalDateTime endDate) {
		translocationDao.getTranslocations(licensePlate, startDate, endDate);
		throw new NotImplementedException();
		//ToDo: Split the translocations to journeys based on something (we don't know what yet).
	}
}
