package com.qdcares.smartmq.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 聊天用户关系表（用于用户检索聊天列表）
 * @Author: LiuYiQiang
 * @Date: 9:08 2019/10/11
 */
@Entity
@Table(name = "IM_CHAT_USER")
@Getter
@Setter
public class ImChatUser {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 聊天ID
     */
    @Column(name = "CHAT_ID",precision = 11)
    private Integer chatId;

    /**
     * 参与聊天的用户ID
     */
    @Column(name = "USER_ID",precision = 11)
    private Integer userId;

}
