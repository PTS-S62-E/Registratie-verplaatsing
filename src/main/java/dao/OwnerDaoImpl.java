package dao;

import entities.Owner;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OwnerDaoImpl implements OwnerDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Owner getOwner(long id) {
		return em.find(Owner.class, id);
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
