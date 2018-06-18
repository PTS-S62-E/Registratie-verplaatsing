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
import java.util.logging.Logger;

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
                        Logger.getLogger(getClass().getName()).warning(data);
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            Logger.getLogger(getClass().getName()).warning("Will decode now");
                            TranslocationRequesterDto dto =  mapper.readValue(data, TranslocationRequesterDto.class);
                            Logger.getLogger(getClass().getName()).warning("Data decoded into dto");
                            return dto;
                        } catch (Exception e) {
                            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Couldn't parse request to send translocations").build());
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public boolean willDecode(String s) {
                        Logger.getLogger(getClass().getName()).warning(s);
                        return true;
                    }
                }).autoAck();
    }
}
