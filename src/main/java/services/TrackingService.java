package services;

import exceptions.TrackingException;
import exceptions.VehicleException;

public interface TrackingService {
	void createTracking(String licensePlate) throws TrackingException, VehicleException;
	void deleteTracking(String licensePlate) throws TrackingException;
	boolean findTrackings(String licensePlate);
}
