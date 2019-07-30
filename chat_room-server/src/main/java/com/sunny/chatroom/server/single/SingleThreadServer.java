package com.sunny.chatroom.server.single;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadServer {
    public static void main(String[] args) {
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
            //2.获取客户端，阻塞式等待
            Socket socket = serverSocket.accept();
            System.out.println("有客户端连接"+socket.getRemoteSocketAddress());

            //3.获取输入输出流；接收和发送数据
            //接收数据
            InputStream clientInput = socket.getInputStream();
            //字节流转字符流
            Scanner scanner = new Scanner(clientInput);
            String clientData = scanner.nextLine();
            System.out.println("客户端发来的消息:"+clientData);
            //发送数据
            OutputStream clientOutput = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write("你好，我是服务器端\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
