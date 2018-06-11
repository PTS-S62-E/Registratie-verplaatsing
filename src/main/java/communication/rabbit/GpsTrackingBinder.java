package communication.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.TrackingTranslocationDto;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import net.reini.rabbitmq.cdi.DecodeException;
import net.reini.rabbitmq.cdi.Decoder;
import net.reini.rabbitmq.cdi.EventBinder;

import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.util.logging.Logger;

@Dependent
public class GpsTrackingBinder extends EventBinder {
    private final String GPS_TRACKING_QUEUE = "rekeningrijden.simulation.fi";

    @Override
    protected void bindEvents() {
        bind(TrackingTranslocationDto.class)
                .toQueue(this.GPS_TRACKING_QUEUE)
                .withDecoder(new Decoder<TrackingTranslocationDto>() {
            @Override
            public TrackingTranslocationDto decode(byte[] bytes) throws DecodeException {
                String data = new String(bytes);
                ObjectMapper mapper = new ObjectMapper();

                try {
                    return mapper.readValue(data, TrackingTranslocationDto.class);
                } catch (IOException e) {
                    Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to decode received message into TrackingTranslocationDto.").build());
                    Sentry.capture(e);
                }

                return null;
            }

            @Override
            public boolean willDecode(String s) {
                return s.equals("text/plain");
            }
        }).autoAck();
    }
}
