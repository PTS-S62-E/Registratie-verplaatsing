package communication.rabbit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AdministrationDto;
import dto.ForeignVehicleDto;
import dto.TranslocationRequesterDto;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import net.reini.rabbitmq.cdi.*;

import javax.enterprise.context.Dependent;
import java.io.IOException;

@Dependent
public class TranslocationRequesterBinder extends EventBinder {
    @Override
    protected void bindEvents() {
        bind(TranslocationRequesterDto.class)
                .toQueue("fi.registration.translocations.request")
                .withDecoder(new Decoder<TranslocationRequesterDto>() {
                    @Override
                    public TranslocationRequesterDto decode(byte[] bytes) throws DecodeException {
                        String data = new String(bytes);
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            return mapper.readValue(data, TranslocationRequesterDto.class);
                        } catch (IOException e) {
                            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Couldn't parse request to send translocations").build());
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public boolean willDecode(String s) {
                        return s.equals("text/plain");
                    }
                });
    }
}
