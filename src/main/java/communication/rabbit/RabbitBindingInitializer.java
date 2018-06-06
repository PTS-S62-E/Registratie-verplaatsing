package communication.rabbit;

import com.rabbitmq.client.Address;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

@Singleton
@Startup
public class RabbitBindingInitializer {

    @Inject
    private GpsTrackingBinder gpsTrackingBinder;

    @Inject
    private VehicleTrackerBinder vehicleTrackerBinder;

    @PostConstruct
    public void initialize() {
        /**
         * Setup binding for GPS Tracker
         */
        try {
            gpsTrackingBinder.configuration()
                    .addHost("192.168.24.100");

            gpsTrackingBinder.initialize();
        } catch (IOException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to initialize connection for GPS Tracking").build());
            Sentry.capture(e);
        }

        /**
         * Setup binding for tracking a vehicle
         */
//        try {
//            vehicleTrackerBinder.configuration()
//                    .addHost("proftaak@192.168.24.102")
//                    .setUsername("proftaak")
//                    .setPassword("proftaak");
//
//            vehicleTrackerBinder.initialize();
//        } catch (IOException e) {
//            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to initialize connection for Vehicle Tracker").build());
//            Sentry.capture(e);
//        }
    }

}
