package dao;

import entities.Owner;

import java.util.List;

public interface OwnerDao {
	Owner getOwner(long id);
	List<Owner> getOwners(List<Integer> ids);
	Owner getOwner(String licensePlate);
	Owner getOwnerByVehicleId(long vehicleId);
	void createOwner(Owner owner);
	void updateOwner(Owner owner);
}
