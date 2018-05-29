package services;

import exceptions.TrackingException;

public interface TrackingService {
	void createTracking(String licensePlate);
	void deleteTracking(String licensePlate) throws TrackingException;
	boolean findTrackings(String licensePlate);
}
