package dao;

import entities.Vehicle;

public interface VehicleDao {
	Vehicle getVehicle(long id);
	void createVehicle(Vehicle vehicle);
	void updateVehicle(Vehicle vehicle);
}
