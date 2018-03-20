package services;

import entities.Owner;

public interface OwnerService {
	Owner getOwner(long id);
	void createOwner(Owner owner);
	void updateOwner(Owner owner);

}
