package dao;

import entities.Translocation;
import java.time.LocalDateTime;
import java.util.List;

	public interface TranslocationDao {
		/**
		 * Get latest translocation of a vehicle.
		 * @param vehicleId
		 * @return
		 */
		Translocation getLatestTranslocationByVehicleId(long vehicleId);

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
		 * @param translocation
		 */
	void createTranslocation(Translocation translocation);
}
