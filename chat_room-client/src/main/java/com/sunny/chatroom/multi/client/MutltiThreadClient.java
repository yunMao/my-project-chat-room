package com.sunny.chatroom.multi.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MutltiThreadClient {
    public static void main(String[] args) throws IOException {
        //从命令行获取端口号和IP地址
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
        final Socket clientsocket = new Socket(host,port);
        System.out.println("连接上服务器，服务器地址为"+clientsocket.getRemoteSocketAddress());
        Thread writeThread = new Thread(new WriteToServerThread(clientsocket));
        Thread readThread = new Thread(new ReadFromServerThread(clientsocket));
        writeThread.start();
        readThread.start();
    }
}


