package com.foolox.base.db.domain;

import com.foolox.base.constant.disruptor.DbEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "player", indexes = {@Index(columnList = "id", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "player", comment = "玩家信息表")
@AllArgsConstructor
@NoArgsConstructor
public class Player implements DbEvent {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint(11) COMMENT '用户id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", columnDefinition = "varchar(11) COMMENT '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(32) COMMENT '密码'")
    private String password;

    @Column(name = "email", columnDefinition = "varchar(32) COMMENT '邮箱'")
    private String email;

    @Column(name = "gender", columnDefinition = "int(1) COMMENT '性别：0女 1男'")
    private int gender;

    @Column(name = "nickname", columnDefinition = "varchar(32) COMMENT '昵称'")
    private String nickname;

    @Column(name = "userType", columnDefinition = "varchar(32) COMMENT '用户类型'")
    private String userType;

    @Column(name = "mobile", columnDefinition = "varchar(16) COMMENT '手机号'")
    private String mobile;

    @Column(name = "headimg", columnDefinition = "varchar(128) COMMENT '头像url'")
    private String headimg;

    @Column(name = "status", columnDefinition = "int(1) COMMENT '状态 0冻结 1正常'")
    private int status;

    @Column(name = "inviteCode", columnDefinition = "varchar(6) COMMENT '邀请码'")
    private String inviteCode;

    @Column(name = "createTime", columnDefinition = "datetime COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "updateTime", columnDefinition = "datetime COMMENT '最后修改时间'")
    private Date updateTime;

    @Column(name = "lastLoginTime", columnDefinition = "datetime COMMENT '最后登录时间'")
    private Date lastLoginTime;

    @Column(name = "memo", columnDefinition = "varchar(128) COMMENT '备注信息'")
    private String memo;

    @Column(name = "deactiveTime", columnDefinition = "datetime COMMENT '注销时间'")
    private Date deactiveTime;

    @Column(name = "openid", columnDefinition = "varchar(128) COMMENT '微信openID'")
    private String openid;
}