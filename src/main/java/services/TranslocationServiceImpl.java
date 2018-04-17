package services;

import dao.TranslocationDao;
import dao.VehicleDao;
import dto.TranslocationDto;
import entities.Translocation;
import util.LocalDateTimeParser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TranslocationServiceImpl implements TranslocationService{

	@Inject
	TranslocationDao translocationDao;

	@Inject
	VehicleDao vehicleDao;

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

	private void divideTranslocationsIntoJourneys(){

	}
}
