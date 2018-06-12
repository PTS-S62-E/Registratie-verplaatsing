package dao;

import entities.Translocation;
import exceptions.TranslocationException;

import java.time.LocalDateTime;
import java.util.List;

	public interface TranslocationDao {
		/**
		 * Get latest translocation of a vehicle, returns null if no translocation is found.
		 * @param vehicleId
		 * @return
		 */
		Translocation getLatestTranslocationByVehicleId(long vehicleId);

		Translocation getLatestTranslocationBySerialNumber(String serialNumber);

		/**
		 * Get translocations by vehicleId, starting time, and end time.
		 * @param vehicleId
		 * @param startDate
		 * @param endDate
		 * @return list of translocations
		 */
	List<Translocation> getTranslocations(long vehicleId, LocalDateTime startDate, LocalDateTime endDate);

		/**
		 * Get translocations by vehicleId, starting time, and end time.
		 * @param licensePlate
		 * @param startDate
		 * @param endDate
		 * @return list of translocations
		 */
		List<Translocation> getTranslocations(String licensePlate, LocalDateTime startDate, LocalDateTime endDate);

		/**
		 * get translocation by id
		 * @param id
		 * @return translocation
		 */
	Translocation getTranslocation(long id) throws TranslocationException;

		/**
		 * create a new translocation
		 * @param translocation
		 */
	void createTranslocation(Translocation translocation);
}
