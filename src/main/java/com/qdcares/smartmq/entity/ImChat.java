package com.qdcares.smartmq.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 聊天表（群聊在群组创建时创建，单聊在用户聊天前创建）
 * @Author: LiuYiQiang
 * @Date: 15:01 2019/10/10
 */
@Entity
@Table(name = "IM_CHAT")
@Getter
@Setter
public class ImChat {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 群组ID（单聊不设置）
     */
    @Column(name = "GROUP_ID",precision = 11)
    private Integer groupId;

    /**
     * 单聊参与用户1
     */
    @Column(name = "USER_ID1",precision = 11)
    private Integer userId1;

    /**
     * 单聊参与用户2
     */
    @Column(name = "USER_ID2",precision = 11)
    private Integer userId2;

}
