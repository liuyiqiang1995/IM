package com.travelsky.im.client.internal.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 聊天回执消息
 * @Author: LiuYiQiang
 * @Date: 9:56 2019/10/9
 */
@Getter
@Setter
public class ChatReceiptMessage {

    /**
     * 消息唯一标识
     */
    private String uuid;

    /**
     * 消息接收者ID
     */
    private String receiver_id;

    /**
     * 消息接收时间戳
     */
    private long timestamp;

}
