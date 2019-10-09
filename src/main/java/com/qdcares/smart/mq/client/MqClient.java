package com.qdcares.smart.mq.client;

import com.qdcares.smart.mq.enums.MessageTypeEnum;
import com.qdcares.smart.mq.listeners.ConnectSuccessListener;
import com.qdcares.smart.mq.listeners.ExceptionHandleListener;
import com.qdcares.smart.mq.mqtt.client.AbstractMqttClient;
import com.qdcares.smart.mq.mqtt.client.MqttClientProxy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @Description:
 * @Author: LiuYiQiang
 * @Date: 16:41 2019/10/8
 */
@Slf4j
public class MqClient {

    private static String HOST = "tcp://192.168.163.95:1884";
    private static String clientId;
    private AbstractMqttClient mqttClient;
    private static Long quiesceTimeout;

    private ExceptionHandleListener exceptionHandleListener;
    private ConnectSuccessListener connectSuccessListener;

    public MqClient(String HOST, String clientId) {
        this.HOST = HOST;
        this.clientId = clientId;
    }

    public void connect(){
        try {
            mqttClient = new MqttClientProxy(HOST,clientId);
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
                    return sendTextMessage(topicName,message);
                case AUDIO:
                    return sendAudioMessage(topicName,message);
                case VEDIO:
                    return sendVedioMessage(topicName,message);
                case FILE:
                    return sendFileMessage(topicName,message);
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

    private boolean sendTextMessage(String topic,String message){
        return mqttClient.publish(topic,message);
    }

    private boolean sendVedioMessage(String topic,String message){
        return mqttClient.publish(topic,message);
    }

    private boolean sendAudioMessage(String topic,String message){
        return mqttClient.publish(topic,message);
    }

    private boolean sendFileMessage(String topic,String message){
        return mqttClient.publish(topic,message);
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

}
