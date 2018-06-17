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

        bind(AdministrationDto.class)
                .toExchange("")
                .withRoutingKey("fi.antaminen.translocations.receive.internal")
                .withEncoder(new Encoder<AdministrationDto>() {
                    @Override
                    public byte[] encode(AdministrationDto administrationDto) throws EncodeException {
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            String data = mapper.writeValueAsString(administrationDto);
                            return data.getBytes();
                        } catch (JsonProcessingException e) {
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public String contentType() {
                        return "text/plain";
                    }
                });

        bind(ForeignVehicleDto.class)
                .toExchange("")
                .withRoutingKey("fi.antaminen.translocations.receive.foreign")
                .withEncoder(new Encoder<ForeignVehicleDto>() {
                    @Override
                    public byte[] encode(ForeignVehicleDto foreignVehicleDto) throws EncodeException {
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            String data = mapper.writeValueAsString(foreignVehicleDto);
                            return data.getBytes();
                        } catch (JsonProcessingException e) {
                            Sentry.capture(e);
                        }

                        return null;
                    }

                    @Override
                    public String contentType() {
                        return "text/plain";
                    }
                });

    }
}
