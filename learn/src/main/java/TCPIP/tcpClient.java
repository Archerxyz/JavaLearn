package TCPIP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class tcpClient {
    public void toServer(String ip, int port, String data) {
        try {
            //规定指定的ip地址，指定的端口，在本实例里，端口就应该是7775
            Socket socket = new Socket(ip, port);

            //客户端应该先发消息
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.print(data);
            pw.flush();
            socket.shutdownOutput();

            //接收服务器的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String one = "";
            StringBuilder sb = new StringBuilder();
            while ((one = br.readLine()) != null) {
                sb.append(one);
            }
            System.out.println("服务器的消息是：" + sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new tcpClient().toServer("127.0.0.1", 7775, "我是柚西");
    }
}
