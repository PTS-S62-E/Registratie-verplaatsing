package dao;

import entities.Vehicle;
import exceptions.VehicleException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class VehicleDaoImpl implements VehicleDao {

	@PersistenceContext(name = "movementRegistrationPU")
	EntityManager em;

	@Override
	public Vehicle getVehicle(long id) throws VehicleException {
		Vehicle vehicle =  em.find(Vehicle.class, id);

		if (vehicle == null){
			StringBuilder builder = new StringBuilder();
			builder.append("Could not find a vehicle with id ");
			builder.append(Long.toString(id));
			builder.append(".");
			throw new VehicleException(builder.toString());
		}

		else return vehicle;
	}


	/**
	 * Get vehicle by license plate, returns null if there's no vehicle with that license plate found.
	 * @param licensePlate
	 * @return
	 * @throws VehicleException
	 */
	public Vehicle getVehicleByLicensPlate(String licensePlate) throws VehicleException {
		TypedQuery<Vehicle> query =
				em.createNamedQuery("Vehicle.getVehicleByLicensePlate", Vehicle.class);
		List<Vehicle> vehicles = query.setParameter("licensePlate", licensePlate).getResultList();

		if(vehicles == null || vehicles.size() == 0){
			return null;
		}

		if(vehicles.size() > 1){
			throw new VehicleException("There's more than one vehicle registered with this license plate.");
		}

		return vehicles.get(0);
	}

	@Override
	public void createVehicle(Vehicle vehicle) {
		em.persist(vehicle);
	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		em.merge(vehicle);
	}
}
