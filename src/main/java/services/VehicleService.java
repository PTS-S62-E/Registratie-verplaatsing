package services;

import dto.VehicleDto;
import exceptions.CategoryException;
import exceptions.VehicleException;

public interface VehicleService {
	/**
	 * Get vehicle by id.
	 * @param id
	 * @return vehicle
	 */
	VehicleDto getVehicle(long id) throws VehicleException;

	/**
	 * create a new vehicle.
	 * @param vehicleDto
	 */
	void createVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException;

	/**
	 * update a vehicle.
	 * @param vehicleDto
	 */
	void updateVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException;
}
