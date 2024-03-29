package com.travelsky.im.client.internal.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Description: 发布消息的回调类
 * 必须实现MqttCallback的接口并实现对应的相关接口方法
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据
 * @Author: LiuYiQiang
 * @Date: 10:34 2019/10/8
 */
@Slf4j
public class PushCallBack implements MqttCallbackExtended {

    /**
     * 连接完成回调
     */
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("mqtt server connect success...");
    }

    /**
     * 断开连接时调用
     */
    @Override
    public void connectionLost(Throwable cause) {
        //TODO 连接丢失后，一般在这里面进行重连
        log.info("连接断开，开始重连...");
    }

    /**
     * 接收已经订阅的发布
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
    }

    /**
     *  消息发送完成回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("deliveryComplete---------" + token.isComplete());
    }
}
