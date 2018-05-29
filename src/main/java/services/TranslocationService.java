package services;

import dto.AdministrationDto;
import dto.InternalTranslocationDto;
import dto.TranslocationDto;
import exceptions.VehicleException;

import java.time.LocalDateTime;
import java.util.List;

public interface TranslocationService {
	/**
	 * Get translocations by vehicleId, starting time, and end time.
	 * @param vehicleId
	 * @param startDate
	 * @param endDate
	 * @return list of translocations
	List<InternalTranslocationDto> getInternalTranslocationDtos(long vehicleId, LocalDateTime startDate, LocalDateTime endDate);
	 */

	/**
	 * create a new translocation
	 * @param translocationDto
	 */
	void createTranslocation(TranslocationDto translocationDto) throws VehicleException;

	/**
	 * Get AdministratorDTO object
	 * @param vehicleId
	 * @param startDate
	 * @param endDate
	 * @return a DTO object that contains Journeys, that contain Translocations.
	 */
	AdministrationDto getAdministrationDto(long vehicleId, LocalDateTime startDate, LocalDateTime endDate);
}
