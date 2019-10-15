package com.travelsky.im.internal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: 消息表
 * @Author: LiuYiQiang
 * @Date: 15:01 2019/10/10
 */
@Entity
@Table(name = "IM_MESSAGE")
@Getter
@Setter
public class ImMessage {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 发送用户ID
     */
    @Column(name = "SENDER_ID",precision = 11)
    private Integer senderId;

    /**
     * 所属聊天ID
     */
    @Column(name = "CHAT_ID",precision = 11)
    private Integer chatId;

    /**
     * 全局唯一标识
     */
    @Column(name = "UUID",length = 64)
    private String uuid;

    /**
     * 消息类型（TEXT:文本；AUDIO:语音；VIDEO:视频；FILE:文件）
     */
    @Column(name = "TYPE",length = 32)
    private String type;

    /**
     * 消息内容（文本或者文件唯一标识）
     */
    @Column(name = "CONTENT",length = 1024)
    private String content;

    /**
     * 消息发送时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MESSAGE_TIME")
    private Date messageTime;
}
