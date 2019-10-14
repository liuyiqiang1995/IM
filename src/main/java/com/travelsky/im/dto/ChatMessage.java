package com.travelsky.im.dto;

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

    /**
     * 消息唯一标识
     */
    private String uuid;

    /**
     * 消息类型（文本/语音/视频/文件）
     */
    private String type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间戳
     */
    private long timestamp;

}
