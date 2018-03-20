package dao;

import entities.Ownership;

public interface OwnershipDao {
	Ownership getOwnershipByVehicleId(long vehicleId);
	Ownership getOwnershipByOwnerId(long ownerId);
}
