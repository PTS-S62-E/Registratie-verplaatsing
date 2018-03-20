package services;

import dao.OwnershipDao;
import entities.Ownership;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OwnershipServiceImpl implements OwnershipService {

	@Inject
	OwnershipDao ownershipDao;

	@Override
	public Ownership getOwnershipByVehicleId(long id) {
		ownershipDao.getOwnershipByVehicleId(id);
	}

	@Override
	public Ownership getOwnershipByOwnerId(long id) {
		ownershipDao.getOwnershipByOwnerId(id);
	}
}
