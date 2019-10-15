package com.travelsky.im.mqtt.client;

import com.travelsky.im.client.listener.ConnectSuccessListener;
import com.travelsky.im.client.listener.ExceptionHandleListener;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * @Description: MQTT CLIENT
 * @Author: LiuYiQiang
 * @Date: 10:18 2019/10/9
 */
public abstract class AbstractMqttClient {

    /**
     * MQTT客户端
     */
    MqttClient client;

    /**
     * 建立MQTT SERVER连接
     */
    public abstract void doConnnect(MqttConnectOptions options);

    /**
     * 发布主题消息
     * @param topic 主题名称
     * @param message 消息内容
     * @return 消息传递状态
     */
    public abstract boolean publish(String topic, Object message,Integer qos);

    /**
     * 订阅主题
     * @param topic 主题名称
     */
    public abstract void subscribe(String topic);

    /**
     * 订阅主题
     * @param topic 主题名称
     * @param mqttMessageListener 接收消息回调
     */
    public abstract void subscribe(String topic,IMqttMessageListener mqttMessageListener);

    /**
     * 断开MQTT SERVER连接
     * @param quiesceTimeout 连接断开等待时间
     */
    public abstract void disconnect(Long quiesceTimeout);

}
