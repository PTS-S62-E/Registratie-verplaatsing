package services;

import dto.AdministrationDto;
import dto.TranslocationDto;
import exceptions.VehicleException;
import java.time.LocalDateTime;

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

	/**
	 * Get AdministratorDTO object
	 * @param licensePlate
	 * @param startDate
	 * @param endDate
	 * @return a DTO object that contains Journeys, that contain Translocations.
	 */
	AdministrationDto getAdministrationDto(String licensePlate, LocalDateTime startDate, LocalDateTime endDate);
}
