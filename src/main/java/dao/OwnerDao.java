package dao;

import entities.Owner;

public interface OwnerDao {
	Owner getOwner(long id);
	void createOwner(Owner owner);
	void updateOwner(Owner owner);
}
