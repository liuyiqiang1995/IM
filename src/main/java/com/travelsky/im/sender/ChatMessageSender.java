package com.travelsky.im.sender;

import com.travelsky.im.dto.ChatMessage;
import com.travelsky.im.enums.MessageTypeEnum;
import com.travelsky.im.holder.ImContextHolder;
import com.travelsky.im.mqtt.client.AbstractMqttClient;
import com.travelsky.im.util.TopicUtil;
import com.travelsky.im.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Description: 聊天消息发送
 * @Author: LiuYiQiang
 * @Date: 8:31 2019/10/15
 */
@Slf4j
public class ChatMessageSender {

    private AbstractMqttClient mqttClient;

    private static class SingletonClassInstance{
        private static final ChatMessageSender instance = new ChatMessageSender();
    }

    private ChatMessageSender(){
        this.mqttClient = ImContextHolder.getInstance().getMqttClient();
    }

    public static ChatMessageSender getInstance(){
        return ChatMessageSender.SingletonClassInstance.instance;
    }

    /**
     * 发送聊天消息
     * @param chatId 聊天ID
     * @param senderId 消息发送者ID
     * @param msgType 消息类型（文本/音频/视频/文件）
     * @param message 消息内容
     * @return
     */
    public boolean sendChatMessage(String chatId, String senderId, String msgType, String message){
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByMsgType(msgType);
        if(messageTypeEnum == null){
            log.error("type {} id not found",msgType);
            return false;
        }
        String topicName = TopicUtil.publishChatTopicName(chatId,senderId);
        try{
            switch (messageTypeEnum){
                case TEXT:
                    return sendTextMessage(topicName,generateChatMessage(msgType,message));
                case AUDIO:
                    return sendAudioMessage(topicName,generateChatMessage(msgType,message));
                case VEDIO:
                    return sendVedioMessage(topicName,generateChatMessage(msgType,message));
                case FILE:
                    return sendFileMessage(topicName,generateChatMessage(msgType,message));
                default:
                    log.error("type {} id not found",msgType);
                    return false;
            }
        }catch (Exception e){
            if(ImContextHolder.getInstance().getExceptionHandleListener() != null){
                ImContextHolder.getInstance().getExceptionHandleListener().operationException(e);
            }
            return false;
        }
    }

    private boolean sendTextMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message,1);
    }

    private boolean sendVedioMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message,1);
    }

    private boolean sendAudioMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message,1);
    }

    private boolean sendFileMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message,1);
    }

    private ChatMessage generateChatMessage(String type,String content){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUuid(UUIDUtil.getUUID());
        chatMessage.setType(type);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(new Date().getTime());
        return chatMessage;
    }

}
