package services;

import dao.OwnerDao;
import dao.VehicleDao;
import entities.Owner;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OwnerServiceImpl implements OwnerService {

	@Inject
	OwnerDao ownerDao;

	@Override
	public Owner getOwner(long id) {
		return ownerDao.getOwner(id);
	}

	@Override
	public void createOwner(Owner owner) {
		ownerDao.createOwner(owner);
	}

	@Override
	public void updateOwner(Owner owner) {
		ownerDao.updateOwner(owner);
	}
}
