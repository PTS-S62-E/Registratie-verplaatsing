package Communication;

import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.IQueueSubscribeCallback;
import com.pts62.common.finland.communication.QueueConnector;
import exceptions.VehicleException;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import services.TranslocationService;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;

@Startup
@Singleton
public class QueueHandler  {

	private final String country = "FI";
	private final String application = "registration";

	@Inject
	TranslocationService translocationService;

	private QueueConnector connector;

	@PostConstruct
	private void setup() {
		connector = new QueueConnector();
		this.handleRead();
	}

	private void handleRead() {

		//TODO: set actual values
		CommunicationBuilder translocationBuilder = new CommunicationBuilder();
		translocationBuilder.setCountry(country);
		translocationBuilder.setApplication(application);
		translocationBuilder.setMessage("save.translocations");

		CommunicationBuilder comBuilder = new CommunicationBuilder();
		comBuilder.setCountry(country);
		comBuilder.setApplication(application);
		comBuilder.setMessage("*");

		connector.readMessage(translocationBuilder.build(), new IQueueSubscribeCallback() {
			@Override
			public void onMessageReceived(String message) {
				try {
					//TODO: DESERIALIZE JSON MESSAGE TO TRANSLOCATION
					translocationService.createTranslocation(null);
				} catch (VehicleException e) {
					Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Error in generating invoices for foreign countries").build());
					Sentry.capture(e);
				}
			}
		});

		//This method handles messages that get send to
		connector.readMessage(comBuilder.build(), new IQueueSubscribeCallback() {
			@Override
			public void onMessageReceived(String s) {
				Exception e = new Exception("Unable to process MQ request for specific route with data " + s);
				Sentry.getContext().recordBreadcrumb( new BreadcrumbBuilder().setMessage(s).build());
				Sentry.capture(e);
			}
		});
	}
}
