package com.travelsky.im.client.internal.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 聊天消息
 * @Author: LiuYiQiang
 * @Date: 9:56 2019/10/9
 */
@Getter
@Setter
public class ChatChangeMessage {

    /**
     * 群组ID(如果是群聊)
     */
    private String groupId;

    /**
     * 对方用户ID(如果是单聊)
     */
    private String userId;

    /**
     * 聊天名称(群组名或对方用户名)
     */
    private String name;

    /**
     * 描述信息(群组描述或对方用户昵称)
     */
    private String description;

}
