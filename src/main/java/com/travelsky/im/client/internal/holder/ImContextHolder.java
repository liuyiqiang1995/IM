package com.travelsky.im.client.internal.holder;

import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.client.listener.ConnectSuccessListener;
import com.travelsky.im.client.listener.ExceptionHandleListener;
import com.travelsky.im.client.internal.mqtt.client.AbstractMqttClient;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 外部设置Listener
 * @Author: LiuYiQiang
 * @Date: 11:19 2019/10/15
 */
@Getter
@Setter
public class ImContextHolder {

    private static class SingletonClassInstance{
        private static final ImContextHolder instance = new ImContextHolder();
    }

    private ImContextHolder(){}

    public static ImContextHolder getInstance(){
        return SingletonClassInstance.instance;
    }

    /**
     * 登录系统的UserID
     */
    private String userId;
    /**
     * MQTT CLIENT
     */
    private AbstractMqttClient mqttClient;
    /**
     * 连接成功回调
     */
    private ConnectSuccessListener connectSuccessListener;
    /**
     * 异常回调
     */
    private ExceptionHandleListener exceptionHandleListener;
    /**
     * 接收聊天消息回调
     */
    private ChatMessageListener chatMessageListener;

}
