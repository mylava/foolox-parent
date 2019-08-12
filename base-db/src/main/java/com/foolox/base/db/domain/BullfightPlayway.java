package com.foolox.base.db.domain;

import com.foolox.base.constant.annotation.PlaywayMeta;
import com.foolox.base.constant.game.GameType;
import com.foolox.base.constant.game.MarkType;
import com.foolox.base.model.Playway;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 30/07/2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@PlaywayMeta(GameType.BULLFIGHT)
@AllArgsConstructor
@NoArgsConstructor
public class BullfightPlayway extends Playway{
    //局数
    private Integer roundNum=10;
    //叫庄倍数
    private Integer hogDouble;
    //叫分类型
    private MarkType markType;
    // 推注
    private Boolean redouble = true;
    // 同花顺
    private Boolean tonghuashun = false;
    // 顺子牛
    private Boolean shunziniu = false;
    // 通话牛
    private Boolean tonghuaniu = false;
    // 葫芦牛
    private Boolean huluniu = false;
    // 炸弹牛
    private Boolean zhadanniu = false;
    // 五花牛
    private Boolean wuxiaoniu = false;
    // 金牛
    private Boolean jinniu = false;
    // 银牛
    private Boolean yinniu = false;
    //翻倍规则
    private Integer rule;
    //房间结束自动创建
    private Boolean series;
    //最少开牌人数
    private Integer minStart=2;
    //最低抢庄
    private Integer hogFloor=1000;
    //最低下注
    private Integer betFloor=0;
    //开房费用
    private Integer cost;
}
