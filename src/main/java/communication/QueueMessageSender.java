package communication;

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
		System.out.println("sendTrackersToPolice");

		//TODO: DIT WERKT BLIJKBAAR NIET (komt niet aan iig)

		this.builder.setCountry("fi");
		this.builder.setApplication("police");
		this.builder.setMessage("track.vehicle");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String tackingDtoAsJson = mapper.writeValueAsString(trackingInfoDto);
			this.connector.publishMessage(this.builder.build(), tackingDtoAsJson);
		} catch (JsonProcessingException e) {
			Sentry.capture(e);
		}
	}
}
