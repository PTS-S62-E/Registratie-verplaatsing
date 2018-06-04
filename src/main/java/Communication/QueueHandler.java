package Communication;

import Communication.GPSTracker.MQChannel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.IQueueSubscribeCallback;
import com.pts62.common.finland.communication.QueueConnector;
import com.rabbitmq.client.*;
import dto.TrackingTranslocationDto;
import dto.TranslocationDto;
import exceptions.VehicleException;
import io.sentry.Sentry;
import io.sentry.event.Breadcrumb;
import io.sentry.event.BreadcrumbBuilder;
import org.apache.commons.lang3.SerializationUtils;
import services.TranslocationService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

@Startup
@Singleton
public class QueueHandler  {

	private final String country = "fi";
	private final String application = "registration";

	private final String gpsTrackingChannelName = "rekeningrijden.simulation.fi";

	@Inject
	TranslocationService translocationService;

	private QueueConnector connector;

	private Channel gpsTrackingChannel;

	@PostConstruct
	private void setup() {
		System.out.println("---SETTING UP MESSAGE READER---");
		connector = new QueueConnector();

		try {
			gpsTrackingChannel = MQChannel.getInstance().getCurrentChannel(gpsTrackingChannelName);
		} catch (IOException e) {
			Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to create channel to listen for new GPS Tracking messages").build());
			Sentry.capture(e);
		}

		this.handleRead();
		this.handleGpsTrackerMessage();
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

				/**
				 * This code is commented out because the queue for the GPS tracking uses the default exchange.
				 * It is not possible (at this time) to connect to the default exchange with the Osake library.
				 *
				 * An own RabbitMQ implementation that is specific for this project has been created (see {@link MQChannel MQChannel} class)
				 * for more information about this
				 */

//				try {
//					System.out.println("---RECIEVED MESSAGE---");
//					System.out.println("translocation recieved");
//					ObjectMapper mapper = new ObjectMapper();
//					TranslocationDto translocationDto = mapper.readValue(message, TranslocationDto.class);
//					System.out.println("serialnumber=");
//					System.out.println(translocationDto.getSerialNumber());
//					translocationService.createTranslocation(translocationDto);
//				} catch (Exception e) {
//					Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Error in generating invoices for foreign countries").build());
//					Sentry.capture(e);
//				}
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

	private void handleGpsTrackerMessage() {

		Consumer consumer = new DefaultConsumer(gpsTrackingChannel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String messageString = new String(body);
				try {
					ObjectMapper mapper = new ObjectMapper();
					TrackingTranslocationDto ttdto = mapper.readValue(messageString, TrackingTranslocationDto.class);

					TranslocationDto dto = new TranslocationDto();
					dto.setSerialNumber(ttdto.getSerialNumber());
					dto.setLatitude(ttdto.getLat());
					dto.setLongitude(ttdto.getLon());
					dto.setTimestamp(ttdto.getDateTime());
					dto.setCountryCode(ttdto.getCountryCode());

					Logger.getLogger(getClass().getName()).warning("Translocation received!");
					translocationService.createTranslocation(dto);
					Logger.getLogger(getClass().getName()).warning("Translocation saved!");
				} catch (VehicleException e) {
					Sentry.capture(e);
				}

			}
		};

		try {
			this.gpsTrackingChannel.basicConsume(this.gpsTrackingChannelName, true, consumer);
		} catch (IOException e) {
			Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Error in registering basicConsume to gpsTrackingChannel").build());
			Sentry.capture(e);
		}

	}
}
