package com.travelsky.im.internal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 群组表
 * @Author: LiuYiQiang
 * @Date: 15:01 2019/10/10
 */
@Entity
@Table(name = "IM_GROUP")
@Getter
@Setter
public class ImGroup {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 群组名
     */
    @Column(name = "NAME",length = 256)
    private String name;

    /**
     * 群描述信息
     */
    @Column(name = "DESCRIPTION",length = 1024)
    private String description;

    /**
     * 创建用户ID
     */
    @Column(name = "OWNER_ID",precision = 11)
    private Integer ownerId;

    /**
     * 对应的聊天ID
     */
    @Column(name = "CHAT_ID",precision = 11)
    private Integer chatId;

}
