package com.sunny.chatroom.client.single;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadClient {
    public static void main(String[] args) {
        //从命令行获取端口号
        int port = 4444;
        String host = "127.0.0.1";
        if (args.length>0){
            try {
                port = Integer.parseInt(args[0] );

            }catch (NumberFormatException e){
                System.out.println("传入的端口号不正确，采用默认端口号"+port);
            }
        }
        if (args.length>1){
            host = args[1];
        }
        try {
            //1.创建客户端，绑定地址
            Socket clientsocket = new Socket(host,port);
            System.out.println("连接上服务器，服务器地址为"+clientsocket.getRemoteSocketAddress());
            //2.获取输入输出流；接收和发送数据
            //发送数据
            OutputStream clientOutput = clientsocket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write("你好，我是客户端\n");
            writer.flush();
            //接收数据
            InputStream clientInput = clientsocket.getInputStream();
            Scanner scanner = new Scanner(clientInput);
            String serverData = scanner.nextLine();
            System.out.println("服务器发来的消息："+serverData);
            //3.关闭流和客户端
            clientInput.close();
            clientOutput.close();
            clientsocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
