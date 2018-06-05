package communication.handlers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TrackingTranslocationDto;
import dto.TranslocationDto;
import exceptions.VehicleException;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import services.TranslocationService;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.logging.Logger;

@Stateless
public class GpsTrackingHandler {

    @Inject
    private Event<TrackingTranslocationDto> trackingTranslocationDtoEvent;

    @Inject
    private TranslocationService translocationService;

    public void submitEvent(TrackingTranslocationDto ttd) {
        Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("TrackingTranslocationDto submitted, but no implementation for this method in any other applications at this time.").build());
    }

    public void receiveEvent(@Observes TrackingTranslocationDto data) {

        // Transform the TrackingTranslocationDto object to a TranslocationDto
        TranslocationDto dto = new TranslocationDto();
        dto.setSerialNumber(data.getSerialNumber());
        dto.setLatitude(data.getLat());
        dto.setLongitude(data.getLon());
        dto.setTimestamp(data.getDateTime());
        dto.setCountryCode(data.getCountryCode());

        try {
            translocationService.createTranslocation(dto);
        } catch (VehicleException e) {
            Sentry.capture(e);
        }

    }
}
