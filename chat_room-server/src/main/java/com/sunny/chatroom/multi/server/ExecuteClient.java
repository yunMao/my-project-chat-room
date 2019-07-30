package com.sunny.chatroom.multi.server;

/**
 * 服务端处理客户端业务
 * 1. 注册
 * 2. 私聊
 * 3. 群聊
 * 4. 退出
 * 5. 显示当前在线用户
 * 6. 统计用户活跃度
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ExecuteClient implements Runnable {
    //存放在线用户数量，采用ConcurrentHashMap保证线程安全
    private static final Map<String,Socket> ONLINE_USER_MAP = new ConcurrentHashMap<>();
    private final Socket client;

    public ExecuteClient(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true){
            try {
                //获取客户端输入流
                InputStream clientInput = client.getInputStream();
                //字节流转字符流
                Scanner scanner = new Scanner(clientInput);
                //每次读取一行数据
                String lineData = scanner.nextLine();
                /**
                 * 1.注册： userName:name
                 * 2.私聊： private:name:message
                 * 3.群聊： group:message
                 * 4.退出： bye
                 */
                if (lineData.startsWith("userName")){
                    String userName = lineData.split("\\:")[1];
                    this.login(userName,client);
                    continue;
                }
                if (lineData.startsWith("private")){
                    String[] segments = lineData.split("\\:");
                    String userName = segments[1];
                    String message = segments[2];
                    this.privateChat(userName,message);
                    continue;
                }
                if (lineData.startsWith("group")){
                    String message = lineData.split("\\:")[1];
                    this.group(message);
                    continue;
                }
                if (lineData.equals("bye")){
                    this.quit();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //注册功能
    private void login(String userName, Socket password){
        ONLINE_USER_MAP.put(userName,client);
        System.out.println("新用户"+userName+"加入到聊天室,新用户IP："+client.getRemoteSocketAddress());
        printOnlineUser();
        sendMessage(this.client,userName+"恭喜您注册成功\n");
    }

    //私聊功能
    private void privateChat(String userName, String message) {
        //获取当前用户
        String concurrentUserName = this.getConcurretUserName();
        //目标用户
        Socket target = ONLINE_USER_MAP.get(userName);
        if (target != null){
             this.sendMessage(target,concurrentUserName+"对你说"+message+"\n");
        }
    }

    //群聊功能
    private void group(String message) {
        String concurrentUserName = this.getConcurretUserName();
        for (Socket target:ONLINE_USER_MAP.values()) {
            this.sendMessage(target,concurrentUserName+"说"+message);
        }
    }

    //退出功能
    private void quit(){
        String concurrentUserName = this.getConcurretUserName();
        System.out.println(concurrentUserName+"下线了");
        //从map中删除用户
        Socket socket = ONLINE_USER_MAP.remove(concurrentUserName);
        //给客户端一个响应
        this.sendMessage(socket,"bye");
        printOnlineUser();

    }

    //统计在线用户数量
    private void printOnlineUser(){
        System.out.println("当前在线用户数量"+ONLINE_USER_MAP.size());
        System.out.println("在线用户列表如下：");
        for (Map.Entry<String,Socket> entry: ONLINE_USER_MAP.entrySet()){
            System.out.println(entry.getKey());
        }
    }

    //发送消息
    private  void sendMessage(Socket client,String message){
        try {
            OutputStream  targetOutput = client.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(targetOutput);
            writer.write(message+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取当前用户
    private String getConcurretUserName(){
        String concurrentUserNmae = "";
        for (Map.Entry<String,Socket> entry: ONLINE_USER_MAP.entrySet()) {
            if (entry.getValue() == this.client) {
                concurrentUserNmae = entry.getKey();
                break;
            }
        }
        return concurrentUserNmae;
    }
}
