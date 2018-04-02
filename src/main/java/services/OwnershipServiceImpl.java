package services;

import dao.OwnerDao;
import dao.OwnershipDao;
import dao.VehicleDao;
import dto.OwnershipDto;
import entities.Ownership;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class OwnershipServiceImpl implements OwnershipService {

	@Inject
	OwnershipDao ownershipDao;

	@Inject
	VehicleDao vehicleDao;

	@Inject
	OwnerDao ownerDao;

	@Override
	public void createOwnership(OwnershipDto ownershipDto) {
		Ownership ownership = new Ownership(
				ownerDao.getOwner(ownershipDto.getOwnerId()),
				vehicleDao.getVehicle(ownershipDto.getVehicleId()),
				stringToLocalDateTime(ownershipDto.getFromDate()),
				stringToLocalDateTime(ownershipDto.getToDate()));

		ownershipDao.createOwnership(ownership);
	}

	@Override
	public Ownership getOwnershipByVehicleId(long id) {
		return ownershipDao.getOwnershipByVehicleId(id);
	}

	@Override
	public Ownership getOwnershipByOwnerId(long id) {
		return ownershipDao.getOwnershipByOwnerId(id);
	}

	//TODO: make date agreements
	private LocalDateTime stringToLocalDateTime(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(date, formatter);
	}
}
