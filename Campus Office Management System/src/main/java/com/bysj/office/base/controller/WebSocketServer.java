package com.bysj.office.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/imserver/{userId}")
@Component
@Slf4j
public class WebSocketServer {


    private static int onlineCount = 0;

    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private String userId="";


    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);

        }else{
            webSocketMap.put(userId,this);

            addOnlineCount();

        }

        log.info("User connection:"+userId+",Number of people online:" + getOnlineCount());

        try {
            sendMessage("Connection Succeeded");
        } catch (IOException e) {
            log.error("User:"+userId+",Network Error!!!!!!");
        }
    }


    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);

            subOnlineCount();
        }
        log.info("User Exit:"+userId+",Number of people online" + getOnlineCount());
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("User Message:"+userId+",Message:"+message);

        if(StringUtils.isNotBlank(message)){
            try {

                JSONObject jsonObject = JSON.parseObject(message);

                jsonObject.put("fromUserId",this.userId);
                String toUserId=jsonObject.getString("toUserId");

                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                }else{
                    log.error("Requested userId:"+toUserId+"Not on this server");

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("User error:"+this.userId+",Reason:"+error.getMessage());
        error.printStackTrace();
    }

    public  void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }



    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("Send message to:"+userId+"，Message:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("User"+userId+",not online");
        }
    }


    public static void sendInfos(String message) throws IOException {
        log.info("Message:"+message);
        Iterator it=webSocketMap.keySet().iterator();
        while(it.hasNext()){

            String key=it.next().toString();
            webSocketMap.get(key).sendMessage(message);

        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
