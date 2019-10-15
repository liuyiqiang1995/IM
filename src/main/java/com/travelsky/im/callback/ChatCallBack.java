package com.travelsky.im.callback;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.dto.ChatMessage;
import com.travelsky.im.dto.ChatReceiptMessage;
import com.travelsky.im.holder.ImContextHolder;
import com.travelsky.im.util.TopicUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Description: 聊天消息回调
 * @Author: LiuYiQiang
 * @Date: 15:56 2019/10/14
 */
@Slf4j
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

        String sender_id = TopicUtil.getSenderIdForChatTopic(topic);
        String chat_id = TopicUtil.getChatIdForChatTopic(topic);
        if(sender_id == null || chat_id == null){
            log.error("sender_id must not be null");
            return;
        }
        //自己发送的消息无需回执
        if(!sender_id.equals(ImContextHolder.getInstance().getUserId())){
            //发送消息回执
            ChatReceiptMessage chatReceiptMessage = new ChatReceiptMessage();
            chatReceiptMessage.setUuid(chatMessage.getUuid());
            chatReceiptMessage.setTimestamp(chatMessage.getTimestamp());
            chatReceiptMessage.setReceiver_id(sender_id);
            String receiptTopicName = TopicUtil.publishChatReceiptTopicName(chat_id,ImContextHolder.getInstance().getUserId());
            ImContextHolder.getInstance().getMqttClient().publish(receiptTopicName,chatReceiptMessage,1);
        }
    }

}
