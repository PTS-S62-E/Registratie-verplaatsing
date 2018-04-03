package services;


import model.Journey;
import java.time.LocalDateTime;
import java.util.List;

public interface JourneyService {
	List<Journey> getJourneys(String licensePlate, LocalDateTime startDate, LocalDateTime endDate);
}
