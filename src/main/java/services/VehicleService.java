package services;

import dto.VehicleDto;
import entities.Vehicle;
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
	 * @param vehicle
	 */
	void createVehicle(Vehicle vehicle) throws VehicleException;

	/**
	 * update a vehicle.
	 * @param vehicle
	 */
	void updateVehicle(Vehicle vehicle) throws VehicleException;
}
