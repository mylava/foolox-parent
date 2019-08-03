package com.foolox.base.db.domain;

import com.foolox.base.constant.asset.AssetType;
import com.foolox.base.constant.disruptor.DbEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * comment: 用户资产
 *
 * @author: lipengfei
 * @date: 31/07/2019
 */
@Data
@Entity
@Table(name = "asset", indexes = {@Index(columnList = "id", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
public class Asset implements DbEvent {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint(11) COMMENT '资产ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "playerId", columnDefinition = "bigint(11) COMMENT '用户id'")
    private long playerId;

    @Column(name = "balance", columnDefinition = "bigint(11) COMMENT '余额'")
    private long balance;

    @Column(name = "assetType", columnDefinition = "int(1) COMMENT '资产类型'")
    @Enumerated(EnumType.ORDINAL) //EnumType有两个值，ORDINAL表示持久化的为枚举类型的值，STRING表示持久化的为枚举类型的名称。
    private AssetType assetType;

    @Column(name = "status", columnDefinition = "bigint(11) COMMENT '状态，备用字段'")
    private int status;

    @Column(name = "createTime", columnDefinition = "bigint(11) COMMENT '创建时间'")
    private Date createTime;

    @Column(name = "updateTime", columnDefinition = "bigint(11) COMMENT '更新时间'")
    private Date updateTime;
}
