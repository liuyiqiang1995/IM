package com.travelsky.im.client.internal.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 主题工具类
 * @Author: LiuYiQiang
 * @Date: 14:16 2019/10/14
 */
@Slf4j
public class TopicUtil {

    /**
     * 生成发布聊天消息主题名称
     * @param chatId 聊天ID
     * @param senderId 消息发送者ID
     * @return 主题名称
     */
    public static String publishChatTopicName(String chatId, String senderId){
        return "chat/message/" + chatId + "/" + senderId;
    }

    /**
     * 生成订阅聊天消息主题名称
     * @param chatId 聊天ID
     * @return 主题名称
     */
    public static String subscribeChatTopicName(String chatId){
        return "chat/message/" + chatId + "/+";
    }

    /**
     * 生成发布聊天变更通知主题名称
     * @param type 通知类型（CREATE:新增;UPDATE:修改;DELETE:删除）
     * @param chatId 聊天ID
     * @param userId 相关用户ID
     * @return 主题名称
     */
    public static String publishChatChangeTopicName(String type,String chatId, String userId){
        return "chat/change/" + type + "/" + chatId + "/" + userId;
    }

    /**
     * 生成发布聊天消息回执主题名称
     * @param chatId 聊天ID
     * @param senderId 消息发送者ID
     * @return 主题名称
     */
    public static String publishChatReceiptTopicName(String chatId, String senderId){
        return "chat/receipt/" + chatId + "/" + senderId;
    }

    /**
     * 生成订阅聊天变更通知主题名称
     * @param userId 相关用户ID
     * @return 主题名称
     */
    public static String subscribeChatChangeTopicName(String userId){
        return "chat/change/+/+/" + userId;
    }

    /**
     * 生成订阅聊天消息回执主题名称
     * @param userId 相关用户ID
     * @return 主题名称
     */
    public static String subscribeChatReceiptTopicName(String userId){
        return "chat/receipt/+/" + userId;
    }

    /**
     * 获取聊天变更主题类型
     * @param topicName 主题名称
     * @return 变更主题类型
     */
    public static String getTypeForChatChangeTopic(String topicName){
        String[] vars = topicName.split("/");
        if(vars.length > 2){
            return vars[2];
        }else {
            log.error("The parameter is invalid:topicName = {}",topicName);
        }
        return null;
    }

    /**
     * 获取聊天变更主题聊天ID
     * @param topicName 主题名称
     * @return 聊天ID
     */
    public static String getChatIdForChatChangeTopic(String topicName){
        String[] vars = topicName.split("/");
        if(vars.length > 2){
            return vars[2];
        }else {
            log.error("The parameter is invalid:topicName = {}",topicName);
        }
        return null;
    }

    /**
     * 获取聊天主题发送者ID
     * @param topicName 主题名称
     * @return 发送者ID
     */
    public static String getSenderIdForChatTopic(String topicName){
        String[] vars = topicName.split("/");
        if(vars.length > 3){
            return vars[3];
        }else {
            log.error("The parameter is invalid:topicName = {}",topicName);
        }
        return null;
    }

    /**
     * 获取聊天主题聊天ID
     * @param topicName 主题名称
     * @return 聊天ID
     */
    public static String getChatIdForChatTopic(String topicName){
        String[] vars = topicName.split("/");
        if(vars.length > 3){
            return vars[2];
        }else {
            log.error("The parameter is invalid:topicName = {}",topicName);
        }
        return null;
    }
}
