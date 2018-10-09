package com.qdcares.smartmq.client;

import com.qdcares.smartmq.dto.ChatMessage;
import com.qdcares.smartmq.enums.MessageTypeEnum;
import com.qdcares.smartmq.listeners.ConnectSuccessListener;
import com.qdcares.smartmq.listeners.ExceptionHandleListener;
import com.qdcares.smartmq.listeners.MessageReceiverListener;
import com.qdcares.smartmq.mqtt.client.AbstractMqttClient;
import com.qdcares.smartmq.mqtt.client.MqttClientProxy;
import com.qdcares.smartmq.util.UUIDUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Date;

/**
 * @Description:
 * @Author: LiuYiQiang
 * @Date: 16:41 2019/10/8
 */
@Slf4j
public class MqClient {

    @Getter private String HOST;
    @Getter private String clientId;
    private AbstractMqttClient mqttClient;
    private static Long quiesceTimeout;

    private ConnectSuccessListener connectSuccessListener;
    @Getter private ExceptionHandleListener exceptionHandleListener;
    @Getter private MessageReceiverListener messageReceiverListener;

    public MqClient(String HOST, String clientId) {
        this.HOST = HOST;
        this.clientId = clientId;
    }

    public void connect(){
        try {
            mqttClient = new MqttClientProxy(HOST,clientId,messageReceiverListener);
            mqttClient.doConnnect();
            //调用callback listener
            if(connectSuccessListener != null){
                connectSuccessListener.onMessage();
            }
        } catch (MqttException e) {
            //丢给调用方处理
            if(exceptionHandleListener != null){
                exceptionHandleListener.operationException(e);
            }
            log.error("mqttClient initialization failed...",e);
        }
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
        String topicName = getChatTopicName(chatId,senderId);
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
            if(exceptionHandleListener != null){
                exceptionHandleListener.operationException(e);
            }
            return false;
        }
    }

    private boolean sendTextMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message);
    }

    private boolean sendVedioMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message);
    }

    private boolean sendAudioMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message);
    }

    private boolean sendFileMessage(String topic,ChatMessage message){
        return mqttClient.publish(topic,message);
    }

    private ChatMessage generateChatMessage(String type,String content){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUuid(UUIDUtil.getUUID());
        chatMessage.setType(type);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(new Date().getTime());
        return chatMessage;
    }

    /**
     * 获取聊天消息主题名称
     * @param chatId 聊天ID
     * @param senderId 消息发送者ID
     * @return 聊天消息主题名称
     */
    private String getChatTopicName(String chatId, String senderId){
        return "chat/message/" + chatId + "/" + senderId;
    }

    public void disConnect(){
        mqttClient.disConnect(quiesceTimeout);
    }

    public void addExceptionHandleListener(ExceptionHandleListener exceptionHandleListener) {
        this.exceptionHandleListener = exceptionHandleListener;
    }

    public void addConnectSuccessListener(ConnectSuccessListener connectSuccessListener){
        this.connectSuccessListener = connectSuccessListener;
    }

    public void addMessageReceiverListener(MessageReceiverListener messageReceiverListener){
        this.messageReceiverListener = messageReceiverListener;
    }

    /**
     * 订阅消息
     * @param topic 主题名称
     */
    public void subscribe(String topic){
        mqttClient.subscribe(topic);
    }

}
