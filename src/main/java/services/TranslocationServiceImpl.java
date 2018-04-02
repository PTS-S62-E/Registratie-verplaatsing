package services;

import dao.TranslocationDao;
import dao.VehicleDao;
import dto.TranslocationDto;
import entities.Translocation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Stateless
public class TranslocationServiceImpl implements TranslocationService{

	@Inject
	TranslocationDao translocationDao;

	@Inject
	VehicleDao vehicleDao;

	@Override
	public List<Translocation> getTranslocationsByVehicleId(long id) {
		return translocationDao.getTranslocationsByVehicleId(id);
	}

	@Override
	public List<Translocation> getTranslocations(String licensePlate, LocalDateTime startDate, LocalDateTime endDate) {
		return translocationDao.getTranslocations(licensePlate, startDate, endDate);
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
				stringToLocalDateTime(translocationDto.getTimestamp())
		);
		translocationDao.createTranslocation(translocation);
	}

	@Override
	public void updateTranslocation(Translocation translocation) {
		translocationDao.updateTranslocation(translocation);
	}

	//TODO: make date agreements
	private LocalDateTime stringToLocalDateTime(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(date, formatter);
	}
}
