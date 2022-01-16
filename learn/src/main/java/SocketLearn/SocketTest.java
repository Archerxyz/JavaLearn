package SocketLearn;

//import sun.net.www.http.HttpClient;

//import org.apache.http.client.HttpClient;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

public class SocketTest {
    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket("time-a.nist.gov", 13);
             Scanner in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8.name())) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }

        // 另一种写法
        Socket s2 = new Socket();
        s2.connect(new InetSocketAddress("zjy-dev.glodon.com",443),5000);

        System.out.println(s2.getInputStream());

        // 输出IP地址
        InetAddress address = InetAddress.getByName("time-a.nist.gov");
        System.out.println(address);

        // httpClient 支持http/2 需要 JAVA 11

    }
}
