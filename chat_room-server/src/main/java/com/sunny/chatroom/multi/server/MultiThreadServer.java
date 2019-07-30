package com.sunny.chatroom.multi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        //从命令行获取端口号
        int port = 4444;
        if (args.length>0){
            try {
                port = Integer.parseInt(args[0] );
            }catch (NumberFormatException e){
                System.out.println("传入的端口号不正确，采用默认端口号"+port);
            }
        }
        try {
            //1.创建一个服务器端
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("等待客户端连接");
            //每来一个客户端，就提交一个任务
            while (true){
                //2.获取客户端，阻塞式等待
                Socket socket = serverSocket.accept();
                service.submit(new ExecuteClient(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
