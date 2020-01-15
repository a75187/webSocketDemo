package com.example.demo.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc: -.
 * @Author: lipei
 * @CreateDate: 2020/1/13 16:37
 * @Version: 1.0
 */
@ServerEndpoint("/webSocket/{username}")
@Component
public class WebSocket {
    public WebSocket(){
        System.out.println("大搜");
    }

    private static int onlineCount = 0;

    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private HashMap map = new HashMap<String,Object>();

    private Session session;

    private String username;



    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {
        System.out.println(Thread.currentThread().getName()+this);
        this.username = username;
        this.session = session;
        addOnlineCount();

        clients.put(username, this);

        sendMessageTo("你好"+username+"欢迎!",null);
        System.out.println("已连接");

    }



    @OnClose

    public void onClose() throws IOException {

        clients.remove(username);

        subOnlineCount();

    }



    @OnMessage
    public  void onMessage(String message, Session session) throws IOException {
        System.out.println(Thread.currentThread().getName()+this);
        System.out.println("收到前段消息:"+message);
        sendMessageTo("后台已经收到消息",session.getPathParameters().get("username"));

    }



    @OnError

    public void onError(Session session, Throwable error) {

        error.printStackTrace();

    }



    public void sendMessageTo(String message, String To) throws IOException {

        // session.getBasicRemote().sendText(message);

        //session.getAsyncRemote().sendText(message);
        if(To==null){
            session.getAsyncRemote().sendText(message);
        }else{
            WebSocket to = clients.get(To);
            to.session.getAsyncRemote().sendText(message);
        }

    }



    public void sendMessageAll(String message) throws IOException {

        for (WebSocket item : clients.values()) {

            item.session.getAsyncRemote().sendText(message);

        }

    }



    public static synchronized int getOnlineCount() {

        return onlineCount;

    }



    public static synchronized void addOnlineCount() {

        WebSocket.onlineCount++;

    }



    public static synchronized void subOnlineCount() {

        WebSocket.onlineCount--;

    }



    public static synchronized Map<String, WebSocket> getClients() {

        return clients;

    }
}
