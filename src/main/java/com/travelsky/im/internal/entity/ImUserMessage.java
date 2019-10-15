package com.travelsky.im.internal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description: 用户消息表（冗余记录用户接收的消息，便于检索）
 * @Author: LiuYiQiang
 * @Date: 15:01 2019/10/10
 */
@Entity
@Table(name = "IM_USER_MESSAGE")
@Getter
@Setter
public class ImUserMessage {

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
    private Integer userID;

    /**
     * 消息ID
     */
    @Column(name = "MESSAGE_ID",precision = 11)
    private Integer messageId;

    /**
     * 已读状态
     */
    @Column(name = "ACKNOWLEDGED",columnDefinition="tinyint default 0")
    private Boolean acknowledged;
}
