package com.travelsky.im.callback;

import com.alibaba.fastjson.JSONObject;
import com.travelsky.im.client.listener.ChatMessageListener;
import com.travelsky.im.dto.ChatChangeMessage;
import com.travelsky.im.enums.ChatChangeTypeEnum;
import com.travelsky.im.mqtt.client.AbstractMqttClient;
import com.travelsky.im.util.TopicUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @Description: 变更通知主题回调
 * @Author: LiuYiQiang
 * @Date: 14:24 2019/10/14
 */
@Slf4j
public class ChatChangeCallBack implements IMqttMessageListener {

    private AbstractMqttClient mqttClient;

    private ChatMessageListener chatMessageListener;

    public ChatChangeCallBack(AbstractMqttClient mqttClient,ChatMessageListener chatMessageListener) {
        this.mqttClient = mqttClient;
        this.chatMessageListener = chatMessageListener;
    }

    /**
     * 接收到聊天变更通知后，根据变更类型不同做相应处理
     * @param topic 主题名称
     * @param message 消息
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //获取消息主体
        ChatChangeMessage chatChangeMessage = JSONObject.parseObject(message.getPayload(),ChatChangeMessage.class);
        //获取聊天变更通知类型
        String changeType = TopicUtil.getTypeForChatChangeTopic(topic);
        ChatChangeTypeEnum chatChangeTypeEnum = ChatChangeTypeEnum.getByTypeCode(changeType);
        if(chatChangeTypeEnum == null){
            log.error("type {} id not found",changeType);
            return;
        }
        //分类型进行处理
        switch (chatChangeTypeEnum){
            case CREATE:
                //获取聊天变更通知聊天ID
                String chatId = TopicUtil.getChatIdForChatChangeTopic(topic);
                if(chatId == null){
                    log.error("chatId must be not null");
                    return;
                }
                //订阅聊天主题
                mqttClient.subscribe(TopicUtil.subscribeChatTopicName(chatId),new ChatCallBack(chatMessageListener));
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
            default:
                log.error("type {} id not found",changeType);
        }

    }

}
