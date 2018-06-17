package communication.handlers;

import dto.AdministrationDto;
import dto.ForeignVehicleDto;
import dto.TranslocationRequesterDto;
import exceptions.DateException;
import exceptions.TranslocationException;
import io.sentry.Sentry;
import services.TranslocationService;
import services.VehicleService;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

public class TranslocationRequesterHandler {

    @Inject
    private TranslocationService translocationService;

    @Inject
    private VehicleService vehicleService;

    @Inject
    private Event<AdministrationDto> administrationDtoEvent;

    @Inject
    private Event<ForeignVehicleDto> foreignVehicleDtoEvent;

    public void receiveEvent(@Observes TranslocationRequesterDto data) {
        if(data.isForeign()) {
            // Handle all translocations for foreign
            try {
                List<ForeignVehicleDto> result = vehicleService.getForeignVehiclesAndTranslocations(data.getStartDate().atTime(0,0), data.getEndDate().atTime(23, 59));
                for(ForeignVehicleDto dto : result) {
                    this.submitEvent(dto);
                }
            } catch (DateException e) {
                Sentry.capture(e);
            }
        } else {
            // Handle translocation request for internal cars
            if(data.getVehicleId() < 1) {
                Sentry.capture(new TranslocationException("No valid vehicleId provided"));
            } else {
                AdministrationDto result = translocationService.getAdministrationDto(data.getVehicleId(), data.getStartDate().atTime(0, 0), data.getEndDate().atTime(23, 59));
                this.submitEvent(result);
            }
        }
    }

    public void submitEvent(AdministrationDto dto) {
        administrationDtoEvent.fire(dto);
    }

    public void submitEvent(ForeignVehicleDto dto) {
        foreignVehicleDtoEvent.fire(dto);
    }

}
