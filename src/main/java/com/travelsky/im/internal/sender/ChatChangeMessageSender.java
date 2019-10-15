package com.travelsky.im.internal.sender;

import com.travelsky.im.internal.callback.ChatCallBack;
import com.travelsky.im.internal.dto.ChatChangeMessage;
import com.travelsky.im.internal.enums.ChatChangeTypeEnum;
import com.travelsky.im.internal.holder.ImContextHolder;
import com.travelsky.im.internal.mqtt.client.AbstractMqttClient;
import com.travelsky.im.internal.util.TopicUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 聊天变更消息发送
 * @Author: LiuYiQiang
 * @Date: 11:10 2019/10/15
 */
@Slf4j
public class ChatChangeMessageSender {

    private AbstractMqttClient mqttClient;

    private static class SingletonClassInstance{
        private static final ChatChangeMessageSender instance = new ChatChangeMessageSender();
    }

    private ChatChangeMessageSender(){
        this.mqttClient = ImContextHolder.getInstance().getMqttClient();
    }

    public static ChatChangeMessageSender getInstance(){
        return ChatChangeMessageSender.SingletonClassInstance.instance;
    }

    /**
     * 发送聊天消息
     * @param type 通知类型（CREATE:新增;UPDATE:修改;DELETE:删除）
     * @param chatId 聊天ID
     * @param userId 对方用户ID
     * @param groupId 群组ID（如果是群聊）
     * @param name 聊天名称（群组名或对方用户名）
     * @param description 描述信息（群组描述或对方用户昵称）
     * @return 发送是否成功
     */
    public boolean sendChatChangeMessage(String type,String chatId, String userId, String groupId,String name,String description){
        ChatChangeTypeEnum chatChangeTypeEnum = ChatChangeTypeEnum.getByTypeCode(type);
        if(chatChangeTypeEnum == null){
            log.error("type {} id not found",type);
            return false;
        }
        String changeTopicName = TopicUtil.publishChatChangeTopicName(type,chatId,userId);
        ChatChangeMessage chatChangeMessage = generateChatChangeMessage(groupId,userId,name,description);
        String chatTopicName = TopicUtil.subscribeChatTopicName(chatId);
        try{
            switch (chatChangeTypeEnum){
                case CREATE://发布变更通知 + 订阅聊天主题
                    mqttClient.subscribe(chatTopicName,new ChatCallBack(ImContextHolder.getInstance().getChatMessageListener()));
                    return mqttClient.publish(changeTopicName,chatChangeMessage,1);
                case UPDATE://发送变更通知
                    return mqttClient.publish(changeTopicName,chatChangeMessage,1);
                case DELETE://发送变更通知 + 取消订阅
                    mqttClient.unsubscribe(chatTopicName);
                    return mqttClient.publish(changeTopicName,chatChangeMessage,1);
                default:
                    log.error("type {} id not found",type);
                    return false;
            }
        }catch (Exception e){
            if(ImContextHolder.getInstance().getExceptionHandleListener() != null){
                ImContextHolder.getInstance().getExceptionHandleListener().operationException(e);
            }
            return false;
        }
    }

    private ChatChangeMessage generateChatChangeMessage(String groupId, String userId, String name, String description){
        ChatChangeMessage chatChangeMessage = new ChatChangeMessage();
        chatChangeMessage.setGroupId(groupId);
        chatChangeMessage.setUserId(userId);
        chatChangeMessage.setName(name);
        chatChangeMessage.setDescription(description);
        return chatChangeMessage;
    }

}
