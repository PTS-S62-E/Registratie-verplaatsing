package Communication.GPSTracker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQChannel {

    private static MQChannel _instance;
    private Channel channel;

    public static MQChannel getInstance() {
        if(_instance == null) {
            new MQChannel();
        }

        return _instance;
    }

    private MQChannel() {
        _instance = this;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.24.100");

        Connection connection;

        try {
            connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

    public Channel getCurrentChannel(String channelName) throws IOException {
        this.channel.queueDeclare(channelName, true, false, false, null);

        return this.channel;
    }
}
