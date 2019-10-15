package com.travelsky.im.holder;

import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.client.listener.ConnectSuccessListener;
import com.travelsky.im.client.listener.ExceptionHandleListener;
import com.travelsky.im.mqtt.client.AbstractMqttClient;
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

    private AbstractMqttClient mqttClient;
    private ConnectSuccessListener connectSuccessListener;
    private ExceptionHandleListener exceptionHandleListener;
    private ChatMessageListener chatMessageListener;

}
