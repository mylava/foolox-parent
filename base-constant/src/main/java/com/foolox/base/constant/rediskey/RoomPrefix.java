package com.foolox.base.constant.rediskey;

/**
 * comment: 玩家状态相关的缓存前缀
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
public class RoomPrefix extends BasePrefix{

    //一天
    private static final int ONE_DAY = 3600*24;

    private RoomPrefix(String prefix) {
        super(ONE_DAY,prefix);
    }

    //进入房间的用户与ClientSession对应关系  key(playerId -- clientSession)
    //getGamePlayerCacheBean: roomId - clientsession list
    public static final RoomPrefix ROOMNO_PLAYERID_LIST = new RoomPrefix("room:roomNo:playerId:list");

    public static final RoomPrefix ROOMNO_PLAYER_MAP = new RoomPrefix("room:roomNo:player:map");

    //getRoomMappingCacheBean
    //进入房间的用户Id 与 gameRoomId的对应关系 key(playerId -- gameroomId)
    public static final RoomPrefix PLAYERID_GAMEROOMNO = new RoomPrefix("room:playerId:roomNo");

    //getGameRoomCacheBean
    //RoomId 与 GameRoom 的对应关系 key(RoomId -- GameRoom)
    public static final RoomPrefix ROOMNO_GAMEROOM = new RoomPrefix("room:roomNo:gameroom");
    //getQueneCache()
    //玩法 与 房间列表 的对应关系，用于匹配房间
    public static final RoomPrefix ROOM_PLAYWAY_GAMEROOM_LIST = new RoomPrefix("room:playwayId:gameroom_list");

    //getBoardCacheBean roomId -- board
    public static final RoomPrefix ROOM_ROOMID_BOARD = new RoomPrefix("room:roomNo:board");

    //PlayUserESRepository clientsessionid -- gameplayer
//    public static final RoomPrefix ROOM_CLIENTSESSIONID_GAMEPLAYER = new RoomPrefix("room:clientsessionid:gameplayer");

}
