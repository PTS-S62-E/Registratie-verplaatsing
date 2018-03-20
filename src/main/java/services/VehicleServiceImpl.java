package services;

import dao.VehicleDao;
import entities.Vehicle;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VehicleServiceImpl implements VehicleService {

	@Inject
	VehicleDao vehicleDao;

	@Override
	public Vehicle getVehicle(long id) {
		vehicleDao.getVehicle(id);
	}

	@Override
	public void createVehicle(Vehicle vehicle) {
		vehicleDao.createVehicle(vehicle);
	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		vehicleDao.updateVehicle(vehicle);
	}
}
