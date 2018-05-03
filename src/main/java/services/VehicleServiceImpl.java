package services;

import dao.VehicleDao;
import dto.VehicleDto;
import entities.Vehicle;
import exceptions.VehicleException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VehicleServiceImpl implements VehicleService {

	@Inject
	VehicleDao vehicleDao;

	@Override
	public VehicleDto getVehicle(long id) {
		return new VehicleDto(vehicleDao.getVehicle(id));
	}

	@Override
	public void createVehicle(Vehicle vehicle) throws VehicleException {

		if (vehicleDao.getVehicleByLicensPlate(vehicle.getLicensePlate()) != null){
			throw new VehicleException("There's already a vehicle registered with this license plate.");
		}

		vehicleDao.createVehicle(vehicle);

	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		vehicleDao.updateVehicle(vehicle);
	}
}
