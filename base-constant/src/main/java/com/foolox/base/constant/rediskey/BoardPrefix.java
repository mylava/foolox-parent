package com.foolox.base.constant.rediskey;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 02/09/2019
 */
public class BoardPrefix extends BasePrefix{

    //一天
    private static final int ONE_DAY = 3600*24;

    private BoardPrefix(String prefix) {
        super(ONE_DAY,prefix);
    }

    //getBoardCacheBean roomNo -- board
    public static final BoardPrefix ROOMNO_BOARD = new BoardPrefix("board:roomNo:board");

}
