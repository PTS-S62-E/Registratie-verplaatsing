package communication.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConfig;
import com.pts62.common.finland.communication.QueueConnector;
import dto.AdministrationDto;
import dto.ForeignVehicleDto;
import dto.TranslocationRequesterDto;
import exceptions.DateException;
import exceptions.TranslocationException;
import io.sentry.Sentry;
import services.TranslocationService;
import services.VehicleService;
import util.LocalDateTimeParser;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class TranslocationRequesterHandler {

    private ObjectMapper mapper = new ObjectMapper();


    @Inject
    private TranslocationService translocationService;

    @Inject
    private VehicleService vehicleService;

    public void receiveEvent(@Observes TranslocationRequesterDto data) {
        Logger.getLogger(getClass().getName()).warning("Will start generating invoices...");
        if(data.isForeign()) {
            // Handle all translocations for foreign
            Logger.getLogger(getClass().getName()).warning("IsForeign");
            try {
                LocalDateTime startDate = LocalDateTimeParser.stringToLocalDateTime(data.getStartDate());
                LocalDateTime endDate = LocalDateTimeParser.stringToLocalDateTime(data.getEndDate());

                for(LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusDays(2)) {
                    Logger.getLogger(getClass().getName()).warning(date.toString());
                    LocalDateTime tempDate = date.plusDays(2);
                    Logger.getLogger(getClass().getName()).warning(tempDate.toString());
                    List<ForeignVehicleDto> result = vehicleService.getForeignVehiclesAndTranslocations(startDate, tempDate);
                    Logger.getLogger(getClass().getName()).warning("Got an amount of " + result.size() + " dtos");
                    for (ForeignVehicleDto dto : result) {
                        Logger.getLogger("Sending dto's to antaminen");
                        this.submitEvent(dto);
                    }
                }
            } catch (DateException e) {
                Logger.getLogger(getClass().getName()).warning("DateException");
                Sentry.capture(e);
            }
        } else {
            // Handle translocation request for internal cars
            if(data.getVehicleId() < 1) {
                Sentry.capture(new TranslocationException("No valid vehicleId provided"));
            } else {
                AdministrationDto result = translocationService.getAdministrationDto(data.getVehicleId(), LocalDateTimeParser.stringToLocalDateTime(data.getStartDate()), LocalDateTimeParser.stringToLocalDateTime(data.getEndDate()));
                this.submitEvent(result);
            }
        }
    }

    public void submitEvent(AdministrationDto dto) {
        QueueConnector connector = new QueueConnector(new QueueConfig("192.168.24.100", "REKENINGRIJDEN_EXCHANGE", Charset.defaultCharset()));
        CommunicationBuilder builder = new CommunicationBuilder();
        builder.setCountry("fi");
        builder.setApplication("antaminen");
        builder.setMessage("translocations.receive.internal");


        try {
            String data = mapper.writeValueAsString(dto);

            Logger.getLogger(getClass().getName()).warning("Sending AdministrationDto");
            connector.publishMessage(builder.build(), data);
        } catch (JsonProcessingException e) {
            Sentry.capture(e);
        }
    }

    public void submitEvent(ForeignVehicleDto dto) {
        QueueConnector connector = new QueueConnector(new QueueConfig("192.168.24.100", "REKENINGRIJDEN_EXCHANGE", Charset.defaultCharset()));
        CommunicationBuilder builder = new CommunicationBuilder();
        builder.setCountry("fi");
        builder.setApplication("antaminen");
        builder.setMessage("translocations.receive.foreign");

        try {
            String data = mapper.writeValueAsString(dto);

            Logger.getLogger(getClass().getName()).warning("Sending ForeignVehicleDto");
            connector.publishMessage(builder.build(), data);
        } catch (JsonProcessingException e) {
            Sentry.capture(e);
        }
    }

}
