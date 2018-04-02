package dao;

import entities.Ownership;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OwnershipDaoImpl implements OwnershipDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public void createOwnership(Ownership ownership) {
		em.persist(ownership);
	}

	@Override
	public Ownership getOwnershipByVehicleId(long vehicleId) {
		/**
		TypedQuery<Ownership> query =
				em.createNamedQuery("Ownership.getOwnershipByVehicleId", Ownership.class);
		return query.setParameter("vehicleId", vehicleId).getResultList();
		 **/
		throw new NotImplementedException();
	}

	@Override
	public Ownership getOwnershipByOwnerId(long ownerId) {
		/**
		TypedQuery<Ownership> query =
				em.createNamedQuery("Ownership.getOwnershipByOwnerId", Ownership.class);
		return query.setParameter("ownerId", ownerId).getResultList();
		 **/
		throw new NotImplementedException();
	}
}
