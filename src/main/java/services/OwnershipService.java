package services;

import entities.Ownership;

public interface OwnershipService {
	Ownership getOwnershipByVehicleId(long id);
	Ownership getOwnershipByOwnerId(long id);
}
