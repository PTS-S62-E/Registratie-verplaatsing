package dao;

import entities.Tracking;
import exceptions.TrackingException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TrackingDaoImpl implements TrackingDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Tracking findTracking(String licensePlate) {
		TypedQuery<Tracking> query =
				em.createNamedQuery("Tracking.getTracking", Tracking.class);
		query.setParameter("licensePlate", licensePlate);
		query.setParameter("licensePlate", licensePlate);
		List<Tracking> trackings = query.getResultList();

		return trackings.get(0);
	}

	@Override
	public void createTracking(Tracking tracking) {
		em.persist(tracking);
	}

	@Override
	public void deleteTracking(Tracking tracking) {
		em.remove(tracking);
	}
}
