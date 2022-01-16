import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;
//import org.junit.Test;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PressureTest {
    private final static String QUEUE_NAME = "PressureTest1";

    public static void main(String[] args) throws IOException, Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Date oNowTime = new Date();
        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String sDateNowStr = oDateFormat.format(oNowTime);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

//            int prefetchCount = 1;
//            channel.basicQos(prefetchCount);

            String message = "Send Time: " + sDateNowStr + " Content:";

//            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().deliveryMode(2).expiration("600000").build();
            // MessageProperties.PERSISTENT_TEXT_PLAIN

            for (int time = 0; time < 1; time++) {
                // 第一个参数：exchange名，可以为空
                String messageA = message + Integer.toString(time);
                channel.basicPublish("", QUEUE_NAME, true, null, messageA.getBytes());
                System.out.println(" [x] Sent '" + messageA + "'");
            }
        }
    }
}