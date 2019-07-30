package com.sunny.chatroom.multi.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReadFromServerThread implements  Runnable{


        private final Socket clientSocket ;

        public ReadFromServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            InputStream clientInput = null;
            try {
                clientInput = clientSocket.getInputStream();
                Scanner scanner = new Scanner(clientInput);
                while (scanner.hasNext()){
                    //从客户端读取数据
                    String serverData = scanner.next();
                    System.out.println("来自服务器的消息"+serverData);
                    System.out.println(System.currentTimeMillis());;
                    //当用户下线时，服务器响应消息为bye，不再读取来自服务器的数据
                    if (serverData.equals("bye")){
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
