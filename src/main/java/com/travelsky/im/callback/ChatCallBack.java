package com.travelsky.im.callback;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.dto.ChatMessage;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Description: 聊天消息回调
 * @Author: LiuYiQiang
 * @Date: 15:56 2019/10/14
 */
public class ChatCallBack implements IMqttMessageListener{

    private ChatMessageListener chatMessageListener;

    public ChatCallBack(ChatMessageListener chatMessageListener) {
        this.chatMessageListener = chatMessageListener;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        ChatMessage chatMessage = JSONObject.parseObject(message.getPayload(),ChatMessage.class);
        if(chatMessageListener != null){
            chatMessageListener.onMessage(chatMessage);
        }
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息内容 : " + chatMessage.getContent());
    }

}
