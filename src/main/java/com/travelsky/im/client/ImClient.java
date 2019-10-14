package com.travelsky.im.client;

import com.travelsky.im.callback.ChatChangeCallBack;
import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.client.listener.ConnectSuccessListener;
import com.travelsky.im.client.listener.ExceptionHandleListener;
import com.travelsky.im.dto.ChatMessage;
import com.travelsky.im.enums.MessageTypeEnum;
import com.travelsky.im.mqtt.client.AbstractMqttClient;
import com.travelsky.im.mqtt.client.MqttClientProxy;
import com.travelsky.im.util.TopicUtil;
import com.travelsky.im.util.UUIDUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Date;

/**
 * @Description: 即时通讯客户端
 * @Author: LiuYiQiang
 * @Date: 16:41 2019/10/8
 */
@Slf4j
public class ImClient {

    /**
     * 服务地址
     */
    @Getter private String HOST;
    /**
     * userID
     */
    @Getter private String clientId;
    private AbstractMqttClient mqttClient;
    private static Long quiesceTimeout;

    private ConnectSuccessListener connectSuccessListener;
    @Getter private ExceptionHandleListener exceptionHandleListener;
    @Getter private ChatMessageListener chatMessageListener;

    public ImClient(String HOST, String clientId) {
        this.HOST = HOST;
        this.clientId = clientId;
    }

    public void connect(ConnectOptions connectOptions){
        try {
            mqttClient = new MqttClientProxy(HOST,clientId);
            mqttClient.doConnnect(mqttConnectOptions(connectOptions));
            //订阅聊天变更通知主题
            mqttClient.subscribe(TopicUtil.subscribeChatChangeTopicName(clientId),new ChatChangeCallBack(mqttClient,chatMessageListener));
            //连接成功回调
            if(connectSuccessListener != null){
                connectSuccessListener.onMessage();
            }
        } catch (MqttException e) {
            //失败处理回调
            if(exceptionHandleListener != null){
                exceptionHandleListener.operationException(e);
            }
            log.error("mqttClient initialization failed...",e);
        }
    }

    private MqttConnectOptions mqttConnectOptions(ConnectOptions connectOptions){
        //TODO 配置信息，待完善
        MqttConnectOptions options = new MqttConnectOptions();
        options.setKeepAliveInterval(connectOptions.getKeepAliveInterval());
        options.setConnectionTimeout(connectOptions.getConnectionTimeout());
        options.setCleanSession(connectOptions.isCleanSession());
        options.setAutomaticReconnect(connectOptions.isAutomaticReconnect());
        return options;
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

    public void disconnect(){
        mqttClient.disconnect(quiesceTimeout);
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
