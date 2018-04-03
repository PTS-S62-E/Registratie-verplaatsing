package services;

import dto.TranslocationDto;
import entities.Translocation;

import java.time.LocalDateTime;
import java.util.List;

public interface TranslocationService {
	/**
	 * Get translocations by vehicleId, starting time, and end time.
	 * @param vehicleId
	 * @param startDate
	 * @param endDate
	 * @return list of translocations
	 */
	List<Translocation> getTranslocations(long vehicleId, LocalDateTime startDate, LocalDateTime endDate);

	/**
	 * get translocation by id
	 * @param id
	 * @return translocation
	 */
	Translocation getTranslocation(long id);

	/**
	 * create a new translocation
	 * @param translocationDto
	 */
	void createTranslocation(TranslocationDto translocationDto);
}
