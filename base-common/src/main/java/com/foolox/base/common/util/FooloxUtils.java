package com.foolox.base.common.util;


import com.foolox.base.common.disruptor.event.DbEvent;
import com.foolox.base.common.disruptor.event.DbEventType;
import com.foolox.base.common.disruptor.event.OperationEvent;
import com.foolox.base.common.util.redis.UserPrefix;
import com.foolox.base.common.util.redis.RedisUtil;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
public class FooloxUtils {

    private static MD5 md5 = new MD5();

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String md5(String str) {
        return md5.getMD5ofStr(md5.getMD5ofStr(str));
    }

    /**
     * 异步保存DbEvent 到数据库
     *
     * @param event
     * @param mongoRepository
     */
    public static void published(DbEvent event, MongoRepository mongoRepository) {
        published(event, mongoRepository, DbEventType.SAVE);
    }

    /**
     * 异步操作UserEvent数据库
     *
     * @param event
     * @param mongoRepository
     * @param dbEventType
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void published(DbEvent event, MongoRepository mongoRepository, DbEventType dbEventType) {
        Disruptor<OperationEvent> disruptor = (Disruptor<OperationEvent>) ContextUtil.getApplicationContext().getBean("disruptor");
        long seq = disruptor.getRingBuffer().next();
        OperationEvent operationEvent = disruptor.getRingBuffer().get(seq);
        operationEvent.setDbEvent(event);
        operationEvent.setRepository(mongoRepository);
        operationEvent.setDbEventType(dbEventType);
        disruptor.getRingBuffer().publish(seq);
    }

    /**
     * 生成length长度的随机数
     *
     * @param length
     * @return
     */
    public static String getRandomNumberChar(int length) {
        if (length > 1) {
            double random = (Math.random() * 9 + 1);
            int pow = (int) Math.pow(10d, length-1);
            return String.valueOf((int) (random * pow));
        }
        return null;
    }


    /**
     * 通过 userId 获取 ClientSession
     *
     * @param userId
     * @return
     */
//    public static ClientSession getClientSessionById(String userId) {
//        return redisService.get(UserPrefix.PLAYER_ID_CLIENTSESSION, userId, ClientSession.class);
//    }
//
//    /**
//     * 保存 UserId 与 ClientSession 映射关系到缓存
//     *
//     * @param userId
//     */
//    public static void setClientSessionById(String userId, ClientSession clientSession) {
//        redisService.set(UserPrefix.PLAYER_ID_CLIENTSESSION, userId, clientSession);
//    }
//
//    /**
//     * 从缓存中删除 UserId 与 ClientSession 映射关系
//     *
//     * @param userId
//     */
//    public static void delClientSessionById(String userId) {
//        redisService.del(UserPrefix.PLAYER_ID_CLIENTSESSION, userId);
//    }
//
//    /**
//     * 通过 roomId 获取 Board
//     *
//     * @param roomId
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T extends Board> T getBoardByRoomId(String roomId, Class<T> clazz) {
//        return redisService.get(GamePrefix.ROOM_ROOMID_BOARD, roomId, clazz);
//    }
//
//    /**
//     * 保存 roomId 与 Board 映射关系到缓存
//     *
//     * @param roomId
//     * @param board
//     * @return
//     */
//    public static void setBoardByRoomId(String roomId, Board board) {
//        redisService.set(GamePrefix.ROOM_ROOMID_BOARD, roomId, board);
//    }
//
//    /**
//     * 删除 roomId 与 Board 映射关系
//     *
//     * @param roomId
//     * @return
//     */
//    public static void delBoardByRoomId(String roomId) {
//        redisService.del(GamePrefix.ROOM_ROOMID_BOARD, roomId);
//    }
//
//    /**
//     * 通过 userId 获取 RoomId
//     *
//     * @param userId
//     * @return
//     */
//    public static String getRoomIdByUserId(String userId) {
//        return redisService.get(GamePrefix.ROOM_USERID_GAMEROOMID, userId);
//    }
//
//    /**
//     * 保存 userId 与 gameRoomId 映射关系到缓存
//     *
//     * @param userId
//     * @param gameRoomId
//     * @return
//     */
//    public static void setRoomIdByUserId(String userId, String gameRoomId) {
//        redisService.set(GamePrefix.ROOM_USERID_GAMEROOMID, userId, gameRoomId);
//    }
//
//    /**
//     * 从缓存中删除 userId 与 gameRoomId 映射关系
//     *
//     * @param userId
//     * @return
//     */
//    public static void delRoomIdByUserId(String userId) {
//        redisService.del(GamePrefix.ROOM_USERID_GAMEROOMID, userId);
//    }
//
//    /**
//     * 通过 roomId 获取 GameRoom
//     *
//     * @param roomId
//     * @return
//     */
//    public static GameRoom getRoomById(String roomId) {
//        return redisService.get(GamePrefix.ROOM_ROOMID_GAMEROOM, roomId, GameRoom.class);
//    }
//
//    /**
//     * 保存 roomId 与 GameRoom 映射关系到缓存
//     *
//     * @param roomId
//     * @return
//     */
//    public static void setRoomById(String roomId, GameRoom gameRoom) {
//        redisService.set(GamePrefix.ROOM_ROOMID_GAMEROOM, roomId, gameRoom);
//    }
//
//    /**
//     * 通过 playwayId 获取 Playway
//     *
//     * @param playwayId
//     * @return
//     */
//    public static Playway getGamePlaywayById(String playwayId) {
//        return redisService.get(SystemPrefix.CONFIG_ID_PLAYWAY, playwayId, Playway.class);
//    }
//
//    /**
//     * 保存 playwayId 与 Playway 映射关系到缓存
//     *
//     * @param playwayId
//     * @return
//     */
//    public static void setGamePlaywayById(String playwayId, Playway playway) {
//        redisService.set(SystemPrefix.CONFIG_ID_PLAYWAY, playwayId, playway);
//    }
//
//    /**
//     * 通过 roomId 获取 房间内的所有客户端会话 List<ClientSession>
//     *
//     * @param roomId
//     * @return
//     */
//    public static List<ClientSession> getRoomClientSessionList(String roomId) {
//        return redisService.lrange(GamePrefix.ROOM_ROOMID_CLIENTSESSION_LIST, roomId, 0, -1, ClientSession.class);
//    }
//
//    /**
//     * 保存 clientSession 到 roomId 与 List<ClientSession> 映射关系到缓存
//     * @param roomId
//     * @param clientSession
//     */
//    public static void addRoomClientSession(String roomId, ClientSession clientSession) {
//        redisService.lpush(GamePrefix.ROOM_ROOMID_CLIENTSESSION_LIST, roomId, clientSession);
//    }
//
//    /**
//     * 删除 roomId 与 List<ClientSession> 映射关系中的一个元素
//     * @param roomId
//     * @param userId
//     */
//    public static void removeSessionFromRoom(String roomId, String userId) {
//        List<ClientSession> list = redisService.lrange(GamePrefix.ROOM_ROOMID_CLIENTSESSION_LIST, roomId, 0, -1, ClientSession.class);
//        for (ClientSession session : list) {
//            if (userId.equals(session.getUserId())) {
//                list.remove(session);
//            }
//        }
//        redisService.set(GamePrefix.ROOM_ROOMID_CLIENTSESSION_LIST, roomId, list);
//    }
//
//    /**
//     * 通过 roomId 删除 房间内的所有客户端会话 List<ClientSession>
//     *
//     * @param roomId
//     * @return
//     */
//    public static void delRoomClientSessionList(String roomId) {
//        redisService.del(GamePrefix.ROOM_ROOMID_CLIENTSESSION_LIST, roomId);
//    }
//
//
//    /**
//     * 通过 playwayId 获取 AiConfig
//     *
//     * @param playwayId
//     * @return
//     */
//    public static AiConfig getAiConfigByPlaywayId(String playwayId) {
//        return redisService.get(SystemPrefix.CONFIG_AI, playwayId, AiConfig.class);
//    }
//
//    /**
//     * 保存 playwayId 与 AiConfig 映射关系到缓存
//     *
//     * @param playwayId
//     * @return
//     */
//    public static void setAiConfigByPlaywayId(String playwayId, AiConfig aiConfig) {
//        redisService.set(SystemPrefix.CONFIG_AI, playwayId, aiConfig);
//    }
//
//
///*    public static Player getGameplayerBySessionId(String clientSessionId) {
//        return redisService.get(GamePrefix.ROOM_CLIENTSESSIONID_GAMEPLAYER, clientSessionId, GamePlayer.class);
//    }*/
//
//    /**
//     * 通过 clientSessionId 获取 Player
//     * 如果获取不到，则是机器人
//     *
//     * @param clientSessionId
//     * @return
//     */
//    public static Player getPlayerBySessionId(String clientSessionId) {
//        return redisService.get(UserPrefix.PLAYER_CLIENTSESSIONID_PLAYER, clientSessionId, Player.class);
//    }
//
//    /**
//     * 根据玩法，从撮合队列中取出空闲房间
//     *
//     * @return
//     */
//    public static GameRoom pollRoomFromQueue(String playwayId) {
//        GameRoom gameRoom = null;
//        Map<String, GameRoom> map = redisService.hgetAll(GamePrefix.ROOM_PLAYWAY_GAMEROOM_LIST, playwayId, GameRoom.class);
//        for (String s : map.keySet()) {
//            GameRoom room = map.get(s);
//            redisService.hdel(GamePrefix.ROOM_PLAYWAY_GAMEROOM_LIST, playwayId, room.getId());
//            List<ClientSession> clientSessions = FooloxUtils.getRoomClientSessionList(room.getId());
//            if (clientSessions.size() < room.getMaxPlayerNum()) {
//                gameRoom = room;
//                break;
//            }
//        }
//        return gameRoom;
//    }
//
//    /**
//     * 从撮合队列中删除房间
//     *
//     * @return
//     */
//    public static void removeRoomFromQueue(String playwayId, String roomId) {
//        redisService.hdel(GamePrefix.ROOM_PLAYWAY_GAMEROOM_LIST, playwayId, roomId);
//    }
//
//    /**
//     * 根据玩法，从撮合队列中取出空闲房间
//     *
//     * @return
//     */
//    public static void addRoom2Queue(String playwayId, GameRoom gameRoom) {
//        redisService.hset(GamePrefix.ROOM_PLAYWAY_GAMEROOM_LIST, playwayId, gameRoom.getId(), gameRoom);
//    }
//
//    /**
//     * 通过 userId 获取 token
//     * @param userId
//     * @return
//     */
    public static String getTokenByUserId(String userId) {
        return RedisUtil.get(UserPrefix.TOKEN, userId);
    }
//
//    /**
//     * 通过code 获取 sysDict
//     * @param code
//     * @return
//     */
//    public static SysDic getDictByCode(String code) {
//        return redisService.get(SystemPrefix.CONFIG_CODE_SYSDIC, code, SysDic.class);
//    }
//
//    /**
//     * 保存 code  与 sysDict 映射关系到缓存
//     * @param code
//     * @return
//     */
//    public static void setDictByCode(String code, SysDic sysDic) {
//        redisService.set(SystemPrefix.CONFIG_CODE_SYSDIC, code, sysDic);
//    }
//
//    /**
//     * 保存 机构 与 games 映射关系到缓存
//     * @param org
//     * @param gameModelList
//     */
//    public static void setGamesByOrg(String org, List<GameModel> gameModelList) {
//        redisService.set(SystemPrefix.GAMES, org, gameModelList);
//    }
//
//    /**
//     * 通过 机构 从缓存中读取 games
//     * @param org
//     */
//    public static List<GameModel> getGamesByOrg(String org) {
//        //STAY 取不到再从数据库加载一次
//        return redisService.getList(SystemPrefix.GAMES, org, GameModel.class);
//    }
}
