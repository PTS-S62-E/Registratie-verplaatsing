package services;

import dto.ForeignVehicleDto;
import dto.VehicleDto;
import exceptions.CategoryException;
import exceptions.DateException;
import exceptions.VehicleException;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleService {
	/**
	 * Get vehicle by id.
	 * @param id
	 * @return vehicle
	 */
	VehicleDto getVehicle(long id) throws VehicleException;

	/**
	 * Get vehicle by licenseplate
	 * @param licensePlate
	 * @return
	 * @throws VehicleException
	 */
	VehicleDto getVehicle(String licensePlate) throws VehicleException;

	/**
	 * create a new vehicle.
	 * @param vehicleDto
	 */
	void createVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException;

	/**
	 * update a vehicle.
	 * @param vehicleDto
	 */
	void updateVehicle(VehicleDto vehicleDto) throws VehicleException, CategoryException;

	/**
	 * Get a list of foreign vehicleDto with translocationDtos according to a certain time period.
	 * Only returns vehicleDtos that have actually driven within that time period.
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<ForeignVehicleDto> getForeignVehiclesAndTranslocations(LocalDateTime startDate, LocalDateTime endDate) throws DateException;
}
