package dao;

import entities.Vehicle;
import exceptions.VehicleException;
import io.sentry.Sentry;

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
		Vehicle vehicle = em.find(Vehicle.class, id);
		if (vehicle == null){
			StringBuilder builder = new StringBuilder();
			builder.append("There's no vehicle registered with id: ");
			builder.append(id);
			builder.append(".");
			throw new VehicleException(builder.toString());
		}
		return vehicle;
	}

	@Override
	public List<Vehicle> getAllVehiclesFromOtherCountry() {
		TypedQuery<Vehicle> query =
				em.createNamedQuery("Vehicle.getAllVehiclesFromOtherCountries", Vehicle.class);
		List<Vehicle> vehicles = query.setParameter("countryCode", "FI").getResultList();
		return vehicles;
	}

	/**
	 * Get vehicle by license plate, returns null if there's no vehicle with that license plate found.
	 * @param licensePlate
	 * @return
	 * @throws VehicleException
	 */
	public Vehicle getVehicle(String licensePlate) throws VehicleException {
		TypedQuery<Vehicle> query =
				em.createNamedQuery("Vehicle.getVehicleByLicensePlate", Vehicle.class);
		List<Vehicle> vehicles = query.setParameter("licensePlate", licensePlate).getResultList();


		if(vehicles.size() == 0){
			StringBuilder builder = new StringBuilder();
			builder.append("There's no vehicle registered with license plate: ");
			builder.append(licensePlate);
			builder.append(".");
			throw new VehicleException(builder.toString());
		}

		if(vehicles.size() > 1){
			StringBuilder builder = new StringBuilder();
			builder.append("There's more than one vehicle registered with license plate: ");
			builder.append(licensePlate);
			builder.append(".");
			throw new VehicleException(builder.toString());
		}

		return vehicles.get(0);
	}



	/**
	 * check if a licensplate already exists in the database, excludes the license plate passed as a parameter.
	 * pass vehicle id -1 if you don't want to exclude any vehicles.
	 * @param vehicleId
	 * @param licensePlate
	 * @return false if it doesn't exist already.
	 */
	public boolean checkIfLicensePlateAlreadyExists(long vehicleId, String licensePlate) throws VehicleException {
		TypedQuery<Vehicle> query =
				em.createNamedQuery("Vehicle.checkIfLicensePlateAlreadyExists", Vehicle.class);
		List<Vehicle> vehicles = query.setParameter("licensePlate", licensePlate).setParameter("id", vehicleId).getResultList();

		if(vehicles.size() == 1) {
			return true;
		}
		if(vehicles.size() > 1){
			StringBuilder builder = new StringBuilder();
			builder.append("There's more than one vehicle registered with license plate: ");
			builder.append(licensePlate);
			builder.append(". Please contact a system administrator.");
			Sentry.capture(builder.toString());
			throw new VehicleException(builder.toString());
		}
		return false;
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
