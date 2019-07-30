## 项目名称   
网络聊天室

## 项目描述
基于Socket编程的支持群聊，私聊的网络聊天室

## 使用技术
+ Java基础

+ 多线程

+ Socket编程

## 项目功能
 + 注册
 
 + 私聊
 
 + 群聊

 + 退出
 
## 项目实现
  + 服务端：采用线程池调度执行服务器与客户端处理业务逻辑
  + 客户端：采用读写线程，分别处理服务器与客户端的数据发送与接收
  
  ![] (./architecture.png)   
  
## 项目总结
+ 加深对多线程的认识，并使用多线程解决问题
+ 掌握了Java的网络编程的常用类和方法

+ maven工具的使用
+ 熟悉了项目开发的流程，从需求分析，设计和技术的选择，编码，测试

## 优化与扩展
+ 优化
    
   + 参数严格校验
   + 异常信息处理
   + 用户体验
+ 扩展
   + 数据存储到数据库(MySQL)
   + 注册信息存储
   + 增加登录功能(用户名+密码)
   + 历史消息存储   
