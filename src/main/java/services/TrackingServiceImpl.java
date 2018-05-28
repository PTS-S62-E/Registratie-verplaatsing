package services;

import dao.TrackingDao;
import dto.TrackingDto;
import entities.Tracking;
import exceptions.TrackingException;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TrackingServiceImpl implements TrackingService {

	@Inject
	TrackingDao trackingDao;

	public void createTracking(TrackingDto trackingDto){
		trackingDao.createTracking(new Tracking(trackingDto));
	}

	@Override
	public void deleteTracking(String sessionId, String licensePlate) throws TrackingException {
		Tracking tracking = trackingDao.findTracking(sessionId, licensePlate);
		trackingDao.deleteTracking(tracking);
	}

	@Override
	public List<Tracking> findTrackings(String licensePlate) {
		return trackingDao.findTrackings(licensePlate);
	}

	public List<TrackingDto> convertToDto(List<Tracking> trackings) {

		List<TrackingDto> trackingDtos = new ArrayList<>();

		for (Tracking tracking : trackings) {
			trackingDtos.add(new TrackingDto(tracking));
		}

		return trackingDtos;
	}
}
