package services;

import dao.TranslocationDao;
import entities.Translocation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TranslocationServiceImpl implements TranslocationService{

	@Inject
	TranslocationDao translocationDao;

	@Override
	public List<Translocation> getTranslocationsByVehicleId(long id) {
		return translocationDao.getTranslocationsByVehicleId(id);
	}

	@Override
	public Translocation getTranslocation(long id) {
		return translocationDao.getTranslocation(id);
	}

	@Override
	public void createTranslocation(Translocation translocation) {
		translocationDao.createTranslocation(translocation);
	}

	@Override
	public void updateTranslocation(Translocation translocation) {
		translocationDao.updateTranslocation(translocation);
	}
}
