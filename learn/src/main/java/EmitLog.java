import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 交换机的属性配置
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            Date oNowTime = new Date();
            SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
            String sDateNowStr = oDateFormat.format(oNowTime);

            String message = argv.length < 1 ? "info: Hello World!" :
                    String.join(" ", argv);
            message = sDateNowStr + " " + message;

            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
