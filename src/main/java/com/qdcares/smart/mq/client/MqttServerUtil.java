package com.qdcares.smart.mq.client;

import com.qdcares.smart.mq.callback.PushCallBack;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;

/**
 * @Description: Mqtt客户端
 * @Author: LiuYiQiang
 * @Date: 9:31 2019/10/8
 */
@Slf4j
public class MqttServerUtil {

    private MqttClient client;

    public MqttServerUtil(String host, String clientId) throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(host, clientId, new MemoryPersistence());
    }

    public void doConnnect() throws MqttException {
        //TODO 配置信息，待完善
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        // 如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息遗嘱
//        options.setWill(TOPIC, "close".getBytes(), 1, true);
        client.setCallback(new PushCallBack());
        client.connect(options);
    }

    /**
     * 发布消息
     * @param topic
     * @param message
     * @throws MqttException
     */
    public void publish(String topic , String message) throws MqttException, UnsupportedEncodingException {
        MqttTopic mqttTopic = client.getTopic(topic);
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(1);
        mqttMessage.setPayload(message.getBytes("UTF-8"));
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
        System.out.println("message is published completely! " + token.isComplete());
    }

    /**
     * 订阅消息
     * @param topic
     * @throws MqttException
     */
    public void subscribe(String topic) throws MqttException {
        client.subscribe(topic, 1);
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
                log.error("mqttClient disConnect failed...",e);
            }
        }
    }

}
