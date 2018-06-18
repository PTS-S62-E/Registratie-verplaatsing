package communication.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConfig;
import com.pts62.common.finland.communication.QueueConnector;
import dto.TrackingInfoDto;
import io.sentry.Sentry;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.nio.charset.Charset;
import java.util.logging.Logger;

@Stateless
public class VehicleTrackingHandler {

    @Inject
    private Event<TrackingInfoDto> vehicleTrackingEvent;

    public void publishTracking(TrackingInfoDto dto) {
        Logger.getLogger(getClass().getName()).warning("Will send trackinginfo dto");
        QueueConnector connector = new QueueConnector(new QueueConfig("teunwillems.nl", "REKENINGRIJDEN_EXCHANGE", Charset.defaultCharset()));
        CommunicationBuilder builder = new CommunicationBuilder();
        builder.setCountry("fi");
        builder.setApplication("police");
        builder.setMessage("trackingUpdate");

        ObjectMapper mapper = new ObjectMapper();
        try {
            connector.publishMessage(builder.build(), mapper.writeValueAsString(dto) );
        } catch (JsonProcessingException e) {
            Sentry.capture(e);
        }

        Logger.getLogger(getClass().getName()).warning("Tracking info sent!");
    }
}
