package dao;

import entities.Ownership;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OwnershipDaoImpl implements OwnershipDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Ownership getOwnershipByVehicleId(long vehicleId) {
		/**
		TypedQuery<Ownership> query =
				em.createNamedQuery("Ownership.getOwnershipByVehicleId", Ownership.class);
		return query.setParameter("vehicleId", vehicleId).getResultList();
		 **/
		return null;
	}

	@Override
	public Ownership getOwnershipByOwnerId(long ownerId) {
		/**
		TypedQuery<Ownership> query =
				em.createNamedQuery("Ownership.getOwnershipByOwnerId", Ownership.class);
		return query.setParameter("ownerId", ownerId).getResultList();
		 **/
		return null;
	}
}
