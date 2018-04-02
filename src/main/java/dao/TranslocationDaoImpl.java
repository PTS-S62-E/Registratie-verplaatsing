package dao;

import entities.Translocation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TranslocationDaoImpl implements TranslocationDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public List<Translocation> getTranslocationsByVehicleId(long id) {
		TypedQuery<Translocation> query =
				em.createNamedQuery("Translocation.getTranslocationsByVehicleId", Translocation.class);
		return query.setParameter("vehicleId", id).getResultList();
	}

	@Override
	public List<Translocation> getTranslocations(String licensePlate, LocalDateTime startTime, LocalDateTime endTime) {
		TypedQuery<Translocation> query =
				em.createNamedQuery("Translocation.getTranslocationsByLicensePlateAndTimePeriod", Translocation.class);
		return query.setParameter("licensePlate", licensePlate)
				.setParameter("startDate", startTime)
				.setParameter("endDate", endTime).getResultList();
	}

	@Override
	public Translocation getTranslocation(long id) {
		return em.find(Translocation.class, id);
	}

	@Override
	public void createTranslocation(Translocation translocation) {
		em.persist(translocation);
	}

	@Override
	public void updateTranslocation(Translocation translocation) {
		em.merge(translocation);
	}
}
