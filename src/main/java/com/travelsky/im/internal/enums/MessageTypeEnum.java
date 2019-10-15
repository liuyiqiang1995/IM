package com.travelsky.im.internal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 消息类型
 */
@AllArgsConstructor
public enum MessageTypeEnum {
    TEXT("text", "文本消息"),
    AUDIO("audio", "语音"),
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
        if(StringUtils.isEmpty(msgType)){
            return null;
        }
        for (MessageTypeEnum messageTypeEnum : values()) {
            if (messageTypeEnum.getMsgType().equals(msgType)) {
                return messageTypeEnum;
            }
        }
        return null;
    }
}
