package TCPIP;

import java.io.*;
import java.net.Socket;

public class ClentThread implements Runnable {
    private Socket socket = null;

    public ClentThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            //接收客户端消息
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //按行读取客户端的消息内容，并且拼接在一起
            StringBuilder sb = new StringBuilder();
            String tmp = "";
            while ((tmp = in.readLine()) != null) {
                sb.append(tmp);
            }
            System.out.println("客户端发送的是：" + sb.toString());
            //转码
            String rc = new String(sb.toString().getBytes(), "UTF-8");

            //将消息返回给客户端
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true);
            out.print(rc);
            //该关的都关掉
            out.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
