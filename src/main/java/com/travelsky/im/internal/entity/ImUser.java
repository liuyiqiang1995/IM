package com.travelsky.im.internal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Description: 用户表
 * @Author: LiuYiQiang
 * @Date: 15:01 2019/10/10
 */
@Entity
@Table(name = "IM_USER")
@Getter
@Setter
public class ImUser {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, precision = 11)
    private Integer id;

    /**
     * 用户名（和认证系统一致）
     */
    @Column(name = "USERNAME",length = 128)
    private String userName;

    /**
     * 用户姓名
     */
    @Column(name = "NAME",length = 256)
    private String name;

    /**
     * 用户昵称（如果有设置则在聊天中显示昵称，否则显示用户姓名）
     */
    @Column(name = "NICKNAME",length = 256)
    private String nickName;

    @Column(name = "AVATAR",columnDefinition="TEXT")
    private String avatar;

}
