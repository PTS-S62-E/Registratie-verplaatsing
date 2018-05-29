package services;

import dao.TrackingDao;
import entities.Tracking;
import exceptions.TrackingException;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TrackingServiceImpl implements TrackingService {

	@Inject
	TrackingDao trackingDao;

	public void createTracking(String licensePlate){
		Tracking tracking = new Tracking(licensePlate);
		trackingDao.createTracking(tracking);
	}

	@Override
	public void deleteTracking(String licensePlate) throws TrackingException {
		Tracking tracking = trackingDao.findTracking(licensePlate);
		trackingDao.deleteTracking(tracking);
	}

	@Override
	public boolean findTrackings(String licensePlate){
		Tracking tracking = trackingDao.findTracking(licensePlate);
		if(tracking == null){
			return false;
		}
		else return true;
	}
}
