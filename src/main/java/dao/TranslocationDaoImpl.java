package dao;

import entities.Translocation;
import exceptions.TranslocationException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TranslocationDaoImpl implements TranslocationDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public List<Translocation> getTranslocations(long vehicleId, LocalDateTime startTime, LocalDateTime endTime) {
		TypedQuery<Translocation> query =
				em.createNamedQuery("Translocation.getTranslocationsByVehicleIdAndTimePeriod", Translocation.class);
		return query.setParameter("vehicleId", vehicleId)
				.setParameter("startDate", startTime)
				.setParameter("endDate", endTime).getResultList();
	}

	@Override
	public Translocation getLatestTranslocationByVehicleId(long vehicleId) {
		TypedQuery<Translocation> query =
				em.createNamedQuery("Translocation.getTranslocationsByVehicleId", Translocation.class);
		List<Translocation> translocations = query.setParameter("vehicleId", vehicleId).setMaxResults(1).getResultList();

		if (translocations == null || translocations.size() == 0){
			return null;
		}

		return translocations.get(0);
	}

	@Override
	public List<Translocation> getTranslocations(String licensePlate, LocalDateTime startTime, LocalDateTime endTime) {
		Query q = em.createQuery("SELECT t FROM Translocation t WHERE t.vehicle.licensePlate = :licensePlate AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp ASC");
		return q.setParameter("licensePlate", licensePlate)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime)
				.getResultList();
	}

	@Override
	public Translocation getLatestTranslocationBySerialNumber(String serialNumber) {
		Query q = em.createQuery("SELECT t FROM Translocation t WHERE t.vehicle.serialNumber = :serialNumber");
		List<Translocation> translocations = q.setParameter("serialNumber", serialNumber).setMaxResults(1).getResultList();

		if (translocations == null || translocations.size() == 0){
			return null;
		}

		return translocations.get(0);
	}

	@Override
	public Translocation getTranslocation(long id) throws TranslocationException {
		Translocation translocation = em.find(Translocation.class, id);

		if (translocation == null){
			StringBuilder builder = new StringBuilder();
			builder.append("Could not find a translocation with id ");
			builder.append(Long.toString(id));
			builder.append(".");
			throw new TranslocationException(builder.toString());
		}

		else return translocation;
	}

	@Override
	public void createTranslocation(Translocation translocation) {
		em.persist(translocation);
	}
}
