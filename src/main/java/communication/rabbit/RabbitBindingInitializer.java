package communication.rabbit;

import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;

@Singleton
@Startup
public class RabbitBindingInitializer {

    @Inject
    private GpsTrackingBinder gpsTrackingBinder;

    @Inject
    private TranslocationRequesterBinder translocationRequesterBinder;

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

        try {
            translocationRequesterBinder.configuration()
                    .addHost("192.168.24.100");

            gpsTrackingBinder.initialize();
        } catch (IOException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to initialize connection for translocation requester").build());
            Sentry.capture(e);
        }
    }

}
