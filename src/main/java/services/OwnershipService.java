package services;

import dto.OwnershipDto;
import entities.Ownership;

public interface OwnershipService {
	void createOwnership(OwnershipDto ownershipDto);
	Ownership getOwnershipByVehicleId(long id);
	Ownership getOwnershipByOwnerId(long id);
}
