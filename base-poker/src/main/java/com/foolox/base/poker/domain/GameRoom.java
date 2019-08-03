package com.foolox.base.poker.domain;

import com.foolox.base.constant.disruptor.DbEvent;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.game.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * comment: 游戏房间
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
@Data
@Entity
@Table(name = "game_room", indexes = {@Index(columnList = "id", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
public class GameRoom implements DbEvent {
    private ReentrantLock lock = new ReentrantLock(true);

    @javax.persistence.Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint(11) COMMENT 'ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "roomNo", nullable = false, columnDefinition = "varchar(11) COMMENT '房间号'")
    private String roomNo;

    @Column(name = "creator", nullable = false, columnDefinition = "bigint(11) COMMENT '房间创建人ID'")
    private long creator;

    @Column(name = "club", nullable = false, columnDefinition = "tinyint COMMENT '是否为俱乐部房间'")
    private boolean club;

    @Column(name = "gameType", columnDefinition = "int(1) COMMENT '游戏类型'")
    @Enumerated(EnumType.ORDINAL) //EnumType有两个值，ORDINAL表示持久化的为枚举类型的值，STRING表示持久化的为枚举类型的名称。
    private GameType gameType;

    @Column(name = "playwayId", nullable = false, columnDefinition = "bigint(11) COMMENT '玩法ID'")
    private long playwayId;

    @Transient //这个字段不映射到数据库
    private Playway playway;

    @Column(name = "createTime", columnDefinition = "bigint(11) COMMENT '创建时间'")
    private Date createtime;

    @Column(name = "roomStatus", columnDefinition = "varchar(16) COMMENT '房间状态'")
    @Enumerated(EnumType.STRING)//EnumType有两个值，ORDINAL表示持久化的为枚举类型的值，STRING表示持久化的为枚举类型的名称。
    private RoomStatus roomStatus;

    @Column(name = "currentnum", nullable = false, columnDefinition = "int(2) COMMENT '已玩局数'")
    private int currentnum;

    public void lockRoom() {
        lock.lock();
    }

    public void unlockRoom() {
        lock.unlock();

    }

}
