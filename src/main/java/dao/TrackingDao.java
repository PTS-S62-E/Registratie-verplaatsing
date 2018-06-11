package dao;

import entities.Tracking;
import exceptions.TrackingException;

import java.util.List;

public interface TrackingDao {
	Tracking findTracking(String licensePlate);
	void createTracking(Tracking tracking);
	void deleteTracking(Tracking tracking);
}
