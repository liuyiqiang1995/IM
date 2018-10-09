package com.qdcares.smartmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息类型
 */
@AllArgsConstructor
public enum MessageTypeEnum {
    TEXT("text", "文本消息"),
    AUDIO("audio", "音频"),
    VEDIO("vedio", "视频"),
    FILE("file", "文件");

    /**
     * 消息类型号
     */
    @Getter
    @Setter
    private String msgType;

    /**
     * 消息类型中文描述
     */
    @Getter
    @Setter
    private String msgDescription;

    public static MessageTypeEnum getByMsgType(String msgType) {
        for (MessageTypeEnum messageTypeEnum : values()) {
            if (messageTypeEnum.getMsgType() == msgType) {
                return messageTypeEnum;
            }
        }
        return null;
    }
}
