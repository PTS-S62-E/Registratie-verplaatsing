package dao;

import entities.Owner;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class OwnerDaoImpl implements OwnerDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Owner getOwner(long id) {
		return em.find(Owner.class, id);
	}

	@Override
	public List<Owner> getOwners(List<Integer> ids) {
		TypedQuery<Owner> query =
				em.createNamedQuery("Owner.getOwners", Owner.class);
		return query.setParameter("ids", ids).getResultList();
	}

	@Override
	public Owner getOwner(String licensePlate) {
		return null;
	}

	@Override
	public Owner getOwnerByVehicleId(long vehicleId) {
		return null;
	}

	@Override
	public void createOwner(Owner owner) {
		em.persist(owner);
	}

	@Override
	public void updateOwner(Owner owner) {
		em.merge(owner);
	}
}
