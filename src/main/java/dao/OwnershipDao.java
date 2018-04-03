package dao;

import entities.Ownership;

public interface OwnershipDao {
	void createOwnership(Ownership ownership);
	Ownership getOwnershipByVehicleId(long vehicleId);
	Ownership getOwnershipByOwnerId(long ownerId);
}
