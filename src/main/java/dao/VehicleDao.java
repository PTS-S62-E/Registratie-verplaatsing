package dao;

import entities.Vehicle;

public interface VehicleDao {
	Vehicle getVehicle(long id);
	Vehicle getVehicle(String licensePlate);
	void createVehicle(Vehicle vehicle);
	void updateVehicle(Vehicle vehicle);
}
