package TCPIP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetServer {
    private static void go(){
        int PORT=7775;
        try {
            //指定端口专门处理这件事
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("服务器已启动");
            //死循环，目的是一直保持监听状态
            while (true) {
                //开启监听
                Socket socket = serverSocket.accept();
                //将连接的客户端交给一个线程去处理。每一个客户连接上之后，都会开启一个新的线程。
                Thread t = new Thread(new ClentThread(socket));
                //开启线程
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        go();
    }
}
