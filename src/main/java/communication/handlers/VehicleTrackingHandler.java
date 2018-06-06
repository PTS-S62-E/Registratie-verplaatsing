package communication.handlers;

import dto.TrackingInfoDto;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class VehicleTrackingHandler {

    @Inject
    private Event<TrackingInfoDto> vehicleTrackingEvent;

    public void publishTracking(TrackingInfoDto dto) {
        Logger.getLogger(getClass().getName()).warning("Sending tracking info");
        vehicleTrackingEvent.fire(dto);
    }
}
