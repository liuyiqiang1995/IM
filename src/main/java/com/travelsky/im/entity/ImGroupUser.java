package com.travelsky.im.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 群组用户关系表
 * @Author: LiuYiQiang
 * @Date: 9:08 2019/10/11
 */
@Entity
@Table(name = "IM_GROUP_USER")
@Getter
@Setter
public class ImGroupUser {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 群组ID
     */
    @Column(name = "GROUP_ID",precision = 11)
    private Integer groupId;

    /**
     * 用户ID
     */
    @Column(name = "USER_ID",precision = 11)
    private Integer userId;

    /**
     * 用户在群内昵称（如果没有昵称则取用户昵称或用户姓名）
     */
    @Column(name = "NICKNAME",length = 128)
    private String nickName;

}
