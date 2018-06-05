package services;

import dao.TrackingDao;
import dao.VehicleDao;
import entities.Tracking;
import exceptions.TrackingException;
import exceptions.VehicleException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TrackingServiceImpl implements TrackingService {

	@Inject
	TrackingDao trackingDao;

	@Inject
	VehicleDao vehicleDao;

	public void createTracking(String licensePlate) throws TrackingException, VehicleException {

		if(!vehicleDao.checkIfLicensePlateAlreadyExists(-1, licensePlate)){
			StringBuilder builder = new StringBuilder();
			builder.append("licenseplate: ");
			builder.append(licensePlate);
			builder.append(" doesn't exist.");
			throw new TrackingException(builder.toString());
		}

		Tracking tracking = new Tracking(licensePlate);
		trackingDao.createTracking(tracking);
	}

	@Override
	public void deleteTracking(String licensePlate) throws TrackingException {
		Tracking tracking = trackingDao.findTracking(licensePlate);

		if (tracking == null){
			StringBuilder builder = new StringBuilder();
			builder.append("Tracking with: ");
			builder.append(licensePlate);
			builder.append(" doesn't exist.");
			throw new TrackingException();
		}

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
