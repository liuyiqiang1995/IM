package com.travelsky.im.callback;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.im.dto.ChatReceiptMessage;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Description:
 * @Author: LiuYiQiang
 * @Date: 14:05 2019/10/15
 */
public class ChatReceiptCallBack implements IMqttMessageListener {

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        ChatReceiptMessage chatReceiptMessage = JSONObject.parseObject(message.getPayload(),ChatReceiptMessage.class);
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息内容 : " + chatReceiptMessage.getUuid());

        //TODO 收到回执后标记对应的消息阅读状态
    }
}
