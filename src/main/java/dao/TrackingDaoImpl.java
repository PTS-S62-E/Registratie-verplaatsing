package dao;

import entities.Tracking;
import exceptions.TrackingException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class TrackingDaoImpl implements TrackingDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Tracking findTracking(String sessionId, String licensePlate) throws TrackingException {
		TypedQuery<Tracking> query =
				em.createNamedQuery("Tracking.getTracking", Tracking.class);
		query.setParameter("sessionId", sessionId);
		query.setParameter("licensePlate", licensePlate);
		List<Tracking> trackings = query.getResultList();


		if(trackings.size() == 0){
			StringBuilder builder = new StringBuilder();
			builder.append("There's no tracking registered with sessionId: ");
			builder.append(sessionId);
			builder.append(",");
			builder.append("and licenseplate: ");
			builder.append(licensePlate);
			builder.append(".");
			throw new TrackingException(builder.toString());
		}

		if(trackings.size() > 1){
			StringBuilder builder = new StringBuilder();
			builder.append("There's more than one tracking registered with sessionId: ");
			builder.append(sessionId);
			builder.append(",");
			builder.append("and licenseplate: ");
			builder.append(licensePlate);
			builder.append(".");
			throw new TrackingException(builder.toString());
		}

		return trackings.get(0);
	}

	@Override
	public List<Tracking> findTrackings(String licensePlate){
		TypedQuery<Tracking> query =
				em.createNamedQuery("Tracking.getTrackings", Tracking.class);
		return query.setParameter("licensePlate", licensePlate).getResultList();
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
