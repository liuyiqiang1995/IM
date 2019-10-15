package com.travelsky.im.client;

import com.travelsky.im.callback.ChatChangeCallBack;
import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.client.listener.ConnectSuccessListener;
import com.travelsky.im.client.listener.ExceptionHandleListener;
import com.travelsky.im.holder.ImContextHolder;
import com.travelsky.im.mqtt.client.AbstractMqttClient;
import com.travelsky.im.mqtt.client.MqttClientProxy;
import com.travelsky.im.sender.ChatMessageSender;
import com.travelsky.im.util.TopicUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

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
    @Getter private AbstractMqttClient mqttClient;
    private static Long quiesceTimeout;

    private ConnectSuccessListener connectSuccessListener;
    private ExceptionHandleListener exceptionHandleListener;
    private ChatMessageListener chatMessageListener;

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
            //订阅聊天消息回执主题
            mqttClient.subscribe(TopicUtil.subscribeChatReceiptTopicName(clientId));
            //连接成功回调
            if(connectSuccessListener != null){
                connectSuccessListener.onMessage();
            }
            initSystemContextHolder();
        } catch (MqttException e) {
            //失败处理回调
            if(exceptionHandleListener != null){
                exceptionHandleListener.operationException(e);
            }
            log.error("mqttClient initialization failed...",e);
        }
    }

    public boolean sendChatMessage(String chatId, String senderId, String msgType, String message){
       return ChatMessageSender.getInstance().sendChatMessage(chatId,senderId,msgType,message);
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

    /**
     * 设置MQTT SERVER连接参数
     * @param connectOptions 参数
     * @return
     */
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
     * 初始化SystemContextHolder
     */
    private void initSystemContextHolder(){
        ImContextHolder imContextHolder = ImContextHolder.getInstance();
        imContextHolder.setUserId(clientId);
        imContextHolder.setChatMessageListener(chatMessageListener);
        imContextHolder.setConnectSuccessListener(connectSuccessListener);
        imContextHolder.setExceptionHandleListener(exceptionHandleListener);
        imContextHolder.setMqttClient(mqttClient);
    }

}
