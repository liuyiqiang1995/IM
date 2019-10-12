package com.travelsky.im.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 用户聊天位置表
 * @Author: LiuYiQiang
 * @Date: 9:08 2019/10/11
 */
@Entity
@Table(name = "IM_CHECKPOINT")
@Getter
@Setter
public class ImCheckPoint {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "USER_ID",precision = 11)
    private Integer userId;

    /**
     * 聊天ID
     */
    @Column(name = "CHAT_ID",precision = 11)
    private Integer chatId;

    /**
     * 用户在该聊天中最后一次读取的消息ID
     */
    @Column(name = "LAST_MESSAGE_ID",precision = 11)
    private Integer lastMessageId;

}
