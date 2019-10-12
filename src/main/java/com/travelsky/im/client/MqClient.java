package com.travelsky.im.client;

import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.client.listener.ConnectSuccessListener;
import com.travelsky.im.client.listener.ExceptionHandleListener;
import com.travelsky.im.dto.ChatMessage;
import com.travelsky.im.enums.MessageTypeEnum;
import com.travelsky.im.mqtt.client.AbstractMqttClient;
import com.travelsky.im.mqtt.client.MqttClientProxy;
import com.travelsky.im.util.UUIDUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
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
    @Getter private ChatMessageListener chatMessageListener;

    public MqClient(String HOST, String clientId) {
        this.HOST = HOST;
        this.clientId = clientId;
    }

    public void connect(com.travelsky.im.client.ConnectOptions connectOptions){
        try {
            mqttClient = new MqttClientProxy(HOST,clientId,chatMessageListener);
            //TODO 配置信息，待完善
            MqttConnectOptions options = new MqttConnectOptions();
            options.setKeepAliveInterval(connectOptions.getKeepAliveInterval());
            options.setConnectionTimeout(connectOptions.getConnectionTimeout());
            options.setCleanSession(connectOptions.isCleanSession());
            options.setAutomaticReconnect(connectOptions.isAutomaticReconnect());
            mqttClient.doConnnect(options);
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

    public void addMessageReceiverListener(ChatMessageListener chatMessageListener){
        this.chatMessageListener = chatMessageListener;
    }

    /**
     * 订阅消息
     * @param topic 主题名称
     */
    public void subscribe(String topic){
        mqttClient.subscribe(topic);
    }

}
