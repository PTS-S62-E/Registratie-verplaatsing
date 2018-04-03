package dao;

import entities.Vehicle;

public interface VehicleDao {
	/**
	 * Get vehicle by id.
	 * @param id
	 * @return vehicle
	 */
	Vehicle getVehicle(long id);

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
