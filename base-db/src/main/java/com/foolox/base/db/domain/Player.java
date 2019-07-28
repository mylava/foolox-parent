package com.foolox.base.db.domain;

import com.foolox.base.constant.disruptor.DbEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "player")
public class Player implements DbEvent {
    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private String gender;

    private String nickname;

    private String userType;

    private String mobile;

    private String headimg;
    //0冻结 1正常
    private Integer state;

    private Long balance;

    private String sign;

    private String inviteCode;

    private Date createTime;

    private Date updateTime;

    private Date lastLoginTime;

    private String memo;

    private Date deactiveTime;

    private String openid;

    private Integer disabled;

    private Long coins;      //金币
}