package com.travelsky.im.mqtt.client;

import com.alibaba.fastjson.JSON;
import com.travelsky.im.callback.PushCallBack;
import com.travelsky.im.dto.ChatMessage;
import com.travelsky.im.client.listener.ChatMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @Description: Mqtt客户端
 * @Author: LiuYiQiang
 * @Date: 9:31 2019/10/8
 */
@Slf4j
public class MqttClientProxy extends com.travelsky.im.mqtt.client.AbstractMqttClient {

    private ChatMessageListener chatMessageListener;

    public MqttClientProxy(String host, String clientId,ChatMessageListener chatMessageListener) throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        super.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.chatMessageListener = chatMessageListener;
    }

    public void doConnnect(MqttConnectOptions options){
        client.setCallback(new PushCallBack(chatMessageListener));
        try {
            client.connect(options);
        } catch (MqttException e) {
           log.error("mqtt sever connect failed",e);
        }
    }

    /**
     * 发布消息
     * @param topic 主题名称
     * @param message
     */
    public boolean publish(String topic, ChatMessage message,Integer qos){
        if(topic == null || message == null){
            log.error("topic or message must not be null");
            return false;
        }
        if(qos == null){
            qos = 1;
        }
        MqttTopic mqttTopic = client.getTopic(topic);
        byte[] bytes = JSON.toJSONString(message).getBytes();
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(bytes);
        MqttDeliveryToken token = null;
        try {
            token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
        } catch (MqttException e) {
            log.error("message publish failed",e);
        }
        return token != null && token.isComplete();
    }

    /**
     * 订阅消息
     * @param topic 主题名称
     */
    public void subscribe(String topic){
        try {
            client.subscribe(topic, 1);
        } catch (MqttException e) {
            log.error("topic " + topic + "subscribe failed",e);
        }
    }

    /**
     * 取消订阅
     * @param topic 主题名称
     */
    public void unSubscribe(String topic){
        try {
            client.unsubscribe(topic);
        } catch (MqttException e) {
            log.error("topic " + topic + "unSubscribe failed",e);
        }
    }

    /**
     * 优雅关闭连接
     */
    public void disConnect(Long quiesceTimeout){
        if(client != null && client.isConnected()){
            try {
                if(quiesceTimeout == null || quiesceTimeout == 0L){
                    client.disconnect();
                }else {
                    client.disconnect(quiesceTimeout);
                }
            } catch (MqttException e) {
                log.error("mqttClient disConnect failed",e);
            }
        }
    }

}