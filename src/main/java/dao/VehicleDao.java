package dao;

import entities.Vehicle;
import exceptions.VehicleException;

public interface VehicleDao {
	/**
	 * Get vehicle by id.
	 * @param id
	 * @return vehicle
	 */
	Vehicle getVehicle(long id) throws VehicleException;

	/**
	 * Get vehicle by license plate.
	 * @param licensePlate
	 * @return
	 */
	Vehicle getVehicleByLicensPlate(String licensePlate) throws VehicleException;

	/**
	 * create a new vehicle.
	 * @param vehicle
	 */
	void createVehicle(Vehicle vehicle);

	/**
	 * update a vehicle.
	 * @param vehicle
	 */
	void updateVehicle(Vehicle vehicle);
}
