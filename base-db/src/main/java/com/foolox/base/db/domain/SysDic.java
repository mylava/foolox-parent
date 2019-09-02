package com.foolox.base.db.domain;

import com.foolox.base.constant.disruptor.DbEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 04/08/2019
 */
@Data
@Entity
@Table(name = "sysdic", indexes = {@Index(columnList = "id", unique = true)/*,
    @Index(name = "sysdic_code_unique", columnList="code", unique = true)*/})
@org.hibernate.annotations.Table(appliesTo = "sysdic", comment = "系统字典表")
@AllArgsConstructor
@NoArgsConstructor
public class SysDic implements DbEvent {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint(11) COMMENT 'id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", columnDefinition = "varchar(11) COMMENT '户称'")
    private String name;

    @Column(name = "title", columnDefinition = "varchar(11) COMMENT '标题'")
    private String title = "pub";

    @Column(name = "code", columnDefinition = "varchar(11) COMMENT '代码'")
    private String code;

    @Column(name = "value", columnDefinition = "varchar(128) COMMENT '值'")
    private String value;

    @Column(name = "type", columnDefinition = "varchar(11) COMMENT '类型'")
    private String type;

    @Column(name = "parentId", columnDefinition = "bigint(11) COMMENT '父ID'")
    private long parentId;

    @Column(name = "iconUrl", columnDefinition = "varchar(11) COMMENT 'icon路径'")
    private String iconUrl;

    @Column(name = "enable", columnDefinition = "tinyint(1) COMMENT '是否启用'")
    private boolean enable;

    @Column(name = "sortIndex", columnDefinition = "int(11) COMMENT '排序'")
    private int sortIndex;
}
