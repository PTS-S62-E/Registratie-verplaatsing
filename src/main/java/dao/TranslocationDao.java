package dao;

import entities.Translocation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
	public interface TranslocationDao {
	List<Translocation> getTranslocations(String licensePlate, LocalDateTime startDate, LocalDateTime endDate);
	List<Translocation> getTranslocationsByVehicleId(long id);
	Translocation getTranslocation(long id);
	void createTranslocation(Translocation translocation);
	void updateTranslocation(Translocation translocation);
}
