package Communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.finland.communication.CommunicationBuilder;
import com.pts62.common.finland.communication.QueueConnector;
import dto.TrackingInfoDto;
import io.sentry.Sentry;

public class QueueMessageSender {

	private static QueueMessageSender _instance;
	private QueueConnector connector;
	private CommunicationBuilder builder;

	private QueueMessageSender() {
		_instance = this;

		this.connector = new QueueConnector();
		this.builder = new CommunicationBuilder();
	}

	public static QueueMessageSender getInstance() {
		if(_instance == null) {
			new QueueMessageSender();
		}
		return _instance;
	}

	public void sendTrackersToPolice(TrackingInfoDto trackingInfoDto) {
		//TODO: Setup the message queue correctly so that the actual tracking info will be sent
		this.builder.setCountry("fi");
		this.builder.setApplication("police");
		this.builder.setMessage("track.vehicle");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String invoiceAsJsonString = mapper.writeValueAsString(trackingInfoDto);
			this.connector.publishMessage(this.builder.build(), invoiceAsJsonString);
		} catch (JsonProcessingException e) {
			Sentry.capture(e);
		}
	}
}
