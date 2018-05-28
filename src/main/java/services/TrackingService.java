package services;

import dto.TrackingDto;
import entities.Tracking;
import exceptions.TrackingException;

import java.util.List;

public interface TrackingService {
	void createTracking(TrackingDto trackingDto);
	void deleteTracking(String sessionId, String licensePlate) throws TrackingException;
	List<Tracking> findTrackings(String licensePlate);
	List<TrackingDto> convertToDto(List<Tracking> trackings);
}
