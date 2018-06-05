package communication.rabbit;

import dto.TrackingInfoDto;
import net.reini.rabbitmq.cdi.EventBinder;

import javax.enterprise.context.Dependent;

@Dependent
public class VehicleTrackerBinder extends EventBinder {
    private final String EXCHANGE = "REKENINGRIJDEN_EXCHANGE";
    private final String POLICE_TRACKING_UPDATE_ROUTING = "fi.police.trackingUpdate";

    @Override
    protected void bindEvents() {
        bind(String.class)
                .toExchange(this.EXCHANGE)
                .withRoutingKey(this.POLICE_TRACKING_UPDATE_ROUTING);
    }
}
