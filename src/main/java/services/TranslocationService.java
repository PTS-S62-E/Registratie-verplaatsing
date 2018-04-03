package services;

import dto.TranslocationDto;
import entities.Translocation;

import java.time.LocalDateTime;
import java.util.List;

public interface TranslocationService {
	List<Translocation> getTranslocationsByVehicleId(long id);
	List<Translocation> getTranslocations(String licensePlate, LocalDateTime startDate, LocalDateTime endDate);
	Translocation getTranslocation(long id);
	void createTranslocation(TranslocationDto translocationDto);
	void updateTranslocation(Translocation translocation);
}
