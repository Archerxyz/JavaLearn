import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String routingKey = getRouting();
            String message = getMessage();

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }
    private static String getRouting(){
        return "aa..bb";
    }

    private  static  String getMessage(){
        Date oNowTime = new Date();
        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String sDateNowStr = oDateFormat.format(oNowTime);
        return sDateNowStr;
    }
}