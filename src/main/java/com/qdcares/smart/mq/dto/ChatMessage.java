package com.qdcares.smart.mq.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 聊天消息
 * @Author: LiuYiQiang
 * @Date: 9:56 2019/10/9
 */
@Getter
@Setter
public class ChatMessage {

    private String uuid;

    private String type;

    private String content;

    private long timestamp;

}
