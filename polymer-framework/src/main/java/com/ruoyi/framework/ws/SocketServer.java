package com.ruoyi.framework.ws;


import com.alibaba.fastjson.JSON;
import com.ruoyi.system.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/{id}")
@Component
public class SocketServer {

        private static final Logger log = LoggerFactory.getLogger(SocketServer.class);

    //本地环境测试webSocket
    //https://blog.csdn.net/m0_37899908/article/details/131620267
    //https://stackoverflow.org.cn/websocket/
    //测试方式：ws://127.0.0.1:8080/ws/1


    // 保存链接的session，key为用户名,value为对应的session名
    private static final ConcurrentHashMap<Integer, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * 创建连接
     * 用于监听建立连接，当有客户端与该服务端点建立连接时，将会自回调该注解标注的方法
     *
     * @param session
     * @param id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") Integer id) {
        log.info("用户{}已创建连接", id);
        sessionMap.put(id, session);
        log.info(sessionMap.toString());
    }


    /**
     * 用于监听客户端向服务端发送消息，当客户端与服务端发送消息时，将会回调该注解标注的方法
     *
     * @param msg
     * @param id
     */
    @OnMessage
    public void onMessage(String msg, @PathParam(value = "id") Integer id) {
        log.info("用户{}发来消息:{}", id, msg);
        Message message = JSON.parseObject(msg, Message.class);
        if ("connect".equals(message.getMsg()) && Objects.isNull(message.getTo())) {
            //测试连接
            //根据message中的to属性获取接收消息的用户的session，利用其session将消息转发过去
            Session toSession = sessionMap.get(id);
            if (Objects.nonNull(toSession)) {
                sendMessage(toSession, message.getMsg());
            }
            log.info("用户：" + id + "发送心跳连接");
        } else {
            //根据message中的to属性获取接收消息的用户的session，利用其session将消息转发过去
            Session toSession = sessionMap.get(message.getTo());
            if (Objects.nonNull(toSession)) {
                sendMessage(toSession, message.getMsg());
            }
        }
    }


    /**
     * 用于监听连接关闭，当客户端与该服务端点断开连接时，将会回调该注解标注的方法
     *
     * @param session
     * @param id
     */
    @OnClose
    public void onClose(Session session, @PathParam(value = "id") Integer id) {
        log.info("用户{}已关闭连接", id);
        sessionMap.remove(id);
    }


    /**
     * 用于监听该连接上的任何错误，当客户端与该服务端点的连接发生任何异常，都将回调该注解标注的方法
     * 注意该方法的参数必选Throwable，可选Sessiion以及0-n个String参数，且String参数需要使用@PathParam注解标注
     *
     * @param throwable
     * @param id
     */
    @OnError
    public void onError(Throwable throwable, @PathParam(value = "id") Integer id) {
        log.error(throwable.toString());
        log.error("用户{}连接发生异常", id);
    }


    /**
     * 用来发送消息的方法，参数分别为接收消息的用户的session，和对应的消息
     */
    public void sendMessage(Session toSession, String msg) {
        try {
            toSession.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
