import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;
//import org.junit.Test;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendMQ {
    private final static String QUEUE_NAME = "Hello2";

    public static void main(String[] args) throws IOException, Exception {
        // connection是socket连接的抽象，并且为我们管理协议版本协商（protocol version negotiation），
        // 认证（authentication ）等等事情。这里我们要连接的消息代理在本地，因此我们将host设为“localhost”。
        // 如果我们想连接其他机器上的代理，只需要将这里改为特定的主机名或IP地址。
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Date oNowTime = new Date();
        SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String sDateNowStr = oDateFormat.format(oNowTime);

        // 实际上，可以根据具体情况设置，不是必须
//        factory.setPort(5672); //默认端口号
//        factory.setUsername("guest");//默认用户名
//        factory.setPassword("guest");//默认密码

        // Try-with-resources are not supported at language level '5' 修改pom
        // 用于AutoCloseable
        try(Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
            boolean durable = true;

            // 接下来，我们创建一个channel，绝大部分API方法需要通过调用它来完成。
//            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            // 预取计数：
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            String message = "Send Time: " + sDateNowStr + " Content:";

//            message = message + String.join(" ", args);
            message = message + ".";

            // 消息属性设置：
            // 消息的TTL：
            // deliveryMode(2):持久化消息
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().deliveryMode(2).expiration("60000").build();

            for (int time = 0; time < 10; time ++)
            {
                // 第一个参数：exchange名，可以为空
                String messageA = message + Integer.toString(time);

                //
                channel.basicPublish("", QUEUE_NAME,true, props, messageA.getBytes());
                System.out.println(" [x] Sent '" + messageA + "'");
            }

            // 使用Try-with-resources 无需手动close
//            channel.close();
//            connection.close();
        };

    }
}