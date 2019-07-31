package com.foolox.base.poker.model;

import com.foolox.base.constant.disruptor.DbEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "gameroom")
public class GameRoom implements DbEvent {
    @Id
    private String id; //房间ID
    private String name;
    private String code;
//    private BaseInfo baseInfo;  //基本信息
//    private PlaywayInfo playwayInfo; //玩法信息
//    private ClubInfo clubInfo;  //俱乐部信息
//    private MatchInfo matchInfo; //赛事信息

//    private Map<FooloxClientParamEnum, String> extparams;//房卡模式下的自定义参数
}
