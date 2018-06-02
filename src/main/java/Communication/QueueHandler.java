package Communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.IQueueSubscribeCallback;
import com.pts62.common.finland.communication.QueueConnector;
import dto.TranslocationDto;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import services.TranslocationService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class QueueHandler  {

	private final String country = "fi";
	private final String application = "registration";

	@Inject
	TranslocationService translocationService;

	private QueueConnector connector;

	@PostConstruct
	private void setup() {
		System.out.println("---SETTING UP MESSAGE READER---");
		connector = new QueueConnector();
		this.handleRead();
	}

	private void handleRead() {
		System.out.println("---handleRead---");
		//TODO: set actual values
		CommunicationBuilder translocationBuilder = new CommunicationBuilder();
		translocationBuilder.setCountry(country);
		translocationBuilder.setApplication(application);
		translocationBuilder.setMessage("save.translocation");

		CommunicationBuilder comBuilder = new CommunicationBuilder();
		comBuilder.setCountry(country);
		comBuilder.setApplication(application);
		comBuilder.setMessage("*");

		connector.readMessage(translocationBuilder.build(), new IQueueSubscribeCallback() {
			@Override
			public void onMessageReceived(String message) {
				try {
					System.out.println("---RECIEVED MESSAGE---");
					System.out.println("translocation recieved");
					ObjectMapper mapper = new ObjectMapper();
					TranslocationDto translocationDto = mapper.readValue(message, TranslocationDto.class);
					System.out.println("serialnumber=");
					System.out.println(translocationDto.getSerialNumber());
					translocationService.createTranslocation(translocationDto);
				} catch (Exception e) {
					Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Error in generating invoices for foreign countries").build());
					Sentry.capture(e);
				}
			}
		});

		//This method handles messages that get send to
		connector.readMessage(comBuilder.build(), new IQueueSubscribeCallback() {
			@Override
			public void onMessageReceived(String s) {
				System.out.println("---RECIEVED MESSAGE)---");
				Exception e = new Exception("Unable to process MQ request for specific route with data " + s);
				Sentry.getContext().recordBreadcrumb( new BreadcrumbBuilder().setMessage(s).build());
				Sentry.capture(e);
			}
		});
	}
}
