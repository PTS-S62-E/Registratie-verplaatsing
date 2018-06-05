package communication.handlers;

import dto.TrackingInfoDto;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;


public class VehicleTrackingHandler {
    @Inject
    private Event<String> vehicleTrackingEvent;
}
