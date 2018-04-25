package dao;

import dto.VehicleDto;
import entities.Vehicle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VehicleDaoImpl implements VehicleDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Vehicle getVehicle(long id) {
		return em.find(Vehicle.class, id);
	}

	@Override
	public void createVehicle(Vehicle vehicle) {
		em.persist(vehicle);
	}

	@Override
	public void updateVehicle(Vehicle vehicle){
		em.merge(vehicle);
	}
}
