package communication.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TrackingInfoDto;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import net.reini.rabbitmq.cdi.EncodeException;
import net.reini.rabbitmq.cdi.Encoder;
import net.reini.rabbitmq.cdi.EventBinder;

import javax.enterprise.context.Dependent;

@Dependent
public class VehicleTrackerBinder extends EventBinder {
    private final String EXCHANGE = "amq.default";
    private final String POLICE_TRACKING_UPDATE_ROUTING = "fi.police.trackingUpdate";

    @Override
    protected void bindEvents() {
        bind(TrackingInfoDto.class)
                .toExchange("")
                .withRoutingKey(this.POLICE_TRACKING_UPDATE_ROUTING)
                .withEncoder(new Encoder<TrackingInfoDto>() {
                    @Override
                    public byte[] encode(TrackingInfoDto trackingInfoDto) throws EncodeException {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            String data = mapper.writeValueAsString(trackingInfoDto);
                            return data.getBytes();
                        } catch (JsonProcessingException e) {
                            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to map TrackingInfoDto to JSON").build());
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public String contentType() {
                        return "application/json";
                    }
                });
    }
}
