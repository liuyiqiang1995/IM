package com.qdcares.smart.mq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息类型
 */
@AllArgsConstructor
public enum MessageTypeEnum {
    TEXT(1, "文本消息"),
    AUDIO(2, "音频"),
    VEDIO(3, "视频"),
    FILE(4, "文件");

    /**
     * 消息类型号
     */
    @Getter
    @Setter
    private int msgType;

    /**
     * 消息类型中文描述
     */
    @Getter
    @Setter
    private String msgDescription;
}
