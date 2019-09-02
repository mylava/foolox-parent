package com.foolox.base.db.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
@Data
@Entity
@Table(name = "club_player", indexes = {@Index(columnList = "id", unique = true)})
@org.hibernate.annotations.Table(appliesTo = "club_player", comment = "俱乐部玩家信息表")
@AllArgsConstructor
@NoArgsConstructor
public class ClubPlayer {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "bigint(11) COMMENT 'ID'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", columnDefinition = "bigint(11) COMMENT '用户ID'")
    private Long playerId;

    @Column(name = "club_id", columnDefinition = "bigint(11) COMMENT '俱乐部ID'")
    private Long clubId;

}
