package services;

import entities.Vehicle;

public interface VehicleService {
	Vehicle getVehicle(long id);
	void createVehicle(Vehicle vehicle);
	void updateVehicle(Vehicle vehicle);
}
