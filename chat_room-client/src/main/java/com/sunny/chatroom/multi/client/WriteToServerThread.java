package com.sunny.chatroom.multi.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteToServerThread implements Runnable{
        private Socket clientSocket;

        long statTime ;

        public WriteToServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                //获取客户端输出流
                OutputStream clientOutput = clientSocket.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
                Scanner sc = new Scanner(System.in);
                //向服务器写数据
                while (sc.hasNext()){
                    System.out.println("请输入消息");
                    String message = sc.next();
                    writer.write(message+"\n");
                    System.out.println(System.currentTimeMillis());
                    writer.flush();
                    //客户端要关闭
                    if (message.equals("bye")){
                        clientSocket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
