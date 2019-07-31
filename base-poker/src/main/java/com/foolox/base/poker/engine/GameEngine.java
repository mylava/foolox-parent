package com.foolox.base.poker.engine;

import com.foolox.base.poker.model.GameRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 20/05/2019
 */
@Slf4j
@Component
public class GameEngine {
    /**
     * 解散房间 , 解散的时候，需要验证下，当前对象是否是房间的创建人
     *
     * @param gameRoom
     * @param userId
     */
    public void dismissRoom(GameRoom gameRoom, String userId) {
//        if (gameRoom.getBaseInfo().getMaster().equals(userId)) {
//            log.info("========================dismissRoom========================");
//        }
    }


//    public void gameRequest(ClientSession clientSession, FooloxClient fooloxClient) {
//        GameEvent gameEvent = gameRequest(clientSession.getId(), fooloxClient.getPlaywayId(), fooloxClient, clientSession);
//        if (gameEvent != null) {
//            /**
//             * 举手了，表示游戏可以开始了
//             */
//            if (clientSession != null) {
//                clientSession.setPlayerStatus(PlayerStatus.LOGIN);
//            }
//
//            /**
//             * 游戏状态 ， 玩家请求 游戏房间，获得房间状态后，发送事件给 StateMachine，由 StateMachine驱动 游戏状态 ， 此处只负责通知房间内的玩家
//             * 1、有新的玩家加入
//             * 2、给当前新加入的玩家发送房间中所有玩家信息（不包含隐私信息，根据业务需求，修改ClientSession的字段，剔除掉隐私信息后发送）
//             */
//            ActionTaskUtils.sendEvent(Command.JOIN_ROOM, new JoinRoom(clientSession, gameEvent.getIndex(), gameEvent.getGameRoom().getPlaywayInfo().getMaxPlayerNum(), gameEvent.getGameRoom()), gameEvent.getGameRoom());
//            /**
//             * 发送给单一玩家的消息
//             */
//            ActionTaskUtils.sendPlayers(fooloxClient, gameEvent.getGameRoom());
//            /**
//             * 当前是在游戏中还是未开始
//             */
//            Board board = FooloxUtils.getBoardByRoomId(gameEvent.getRoomid(), Board.class);
//            //不为空，表示游戏进行中
//            if (board != null) {
//                GamePlayer currentPlayer = null;
//                for (GamePlayer gamePlayer : board.getGamePlayers()) {
//                    if (gamePlayer.getPlayuserId().equals(clientSession.getId())) {
//                        currentPlayer = gamePlayer;
//                        break;
//                    }
//                }
//                if (currentPlayer != null) {
//                    //本轮第一个出牌
//                    boolean automic = false;
//                    //（最后出牌不是空 并且 最后出牌玩家是自己） 或 （最后出牌是空 并且 庄家是自己）
//                    if ((board.getLast() != null && board.getLast().getUserid().equals(currentPlayer.getPlayuserId())) || (board.getLast() == null && board.getBanker().equals(currentPlayer.getPlayuserId()))) {
//                        automic = true;
//                    }
//                    //恢复牌局信息
//                    ActionTaskUtils.sendEvent(Command.RECOVERY, new RecoveryData(currentPlayer, board.getLasthands(),
//                            board.getNextplayer() != null ? board.getNextplayer().getNextplayer() : null,
//                            25, automic, board), gameEvent.getGameRoom());
//                }
//            } else {
//                //通知状态
//                GameUtils.getGame(fooloxClient.getPlaywayId()).change(gameEvent); //通知状态机 , 此处应由状态机处理异步执行
//            }
//        }
//    }
//
//
//    /**
//     * 请求开始游戏
//     * <p>
//     * 玩家房间选择， 新请求，游戏撮合， 如果当前玩家是断线重连， 或者是 退出后进入的，则第一步检查是否已在房间
//     * 如果已在房间，直接返回
//     *
//     * @param userId
//     * @param playwayId
//     * @param fooloxClient
//     * @param clientSession
//     * @return
//     */
//    public GameEvent gameRequest(String userId, String playwayId, FooloxClient fooloxClient, ClientSession clientSession) {
//        GameEvent gameEvent = null;
//        //通过userId取出roomID，如果用户不在房间内，则为空
//        String roomId = FooloxUtils.getRoomIdByUserId(userId);
//        //通过传入的 playwayId 从系统配置中取出玩法
//        PlaywayMeta playway = FooloxUtils.getGamePlaywayById(playwayId);
//        boolean needtakequene = false;
//        //如果不为空，则进行后续操作；为空表示参数传递错误
//        if (playway != null) {
//            gameEvent = new GameEvent(playway.getMaxPlayerNum(), playway.getCardsNum());
//            GameRoom gameRoom = null;
//            //已存房内间----不用新建
//            if (!StringUtils.isBlank(roomId) && FooloxUtils.getRoomById(roomId) != null) {
//                gameRoom = FooloxUtils.getRoomById(roomId);
//            } else {
//                //----需要新建房间
//                //房卡游戏 , 创建ROOM
//                if (RoomType.FEE == fooloxClient.getRoomType()) {
//                    gameRoom = this.createGameRoom(playway, userId, true, fooloxClient);
//                } else {
//                    /**
//                     * 大厅游戏,直接从预创建的队列中找不满员的房间
//                     * 1、按照玩法查找
//                     */
//                    gameRoom = FooloxUtils.pollRoomFromQueue(playwayId);
//                    if (gameRoom != null) {
//                        /**
//                         * 修正获取gameroom获取的问题，因为删除房间的时候，为了不损失性能，没有将队列里的房间信息删除，如果有玩家获取到这个垃圾信息
//                         * 则立即进行重新获取房间
//                         */
//                        while (FooloxUtils.getRoomById(roomId) == null) {
//                            gameRoom = FooloxUtils.pollRoomFromQueue(playwayId);
//                            if (gameRoom == null) {
//                                break;
//                            }
//                        }
//                    }
//
//                    if (gameRoom == null) {    //无房间 ， 需要新建一个房间
//                        gameRoom = this.createGameRoom(playway, userId, false, fooloxClient);
//                    } else {
//                        clientSession.setPlayerindex(System.currentTimeMillis());//从后往前坐，房主进入以后优先坐在 首位
//                        needtakequene = true;
//                    }
//                }
//            }
//            if (gameRoom != null) {
//                /**
//                 * 设置游戏当前已经进行的局数
//                 */
//                gameRoom.getPlaywayInfo().setCurrentnum(0);
//                /**
//                 * 更新缓存
//                 */
//                FooloxUtils.setRoomById(gameRoom.getId(), gameRoom);
//                /**
//                 * 如果当前房间到达了最大玩家数量，则不再加入到 撮合队列
//                 */
//                List<ClientSession> haveInRoomPlayerList = FooloxUtils.getRoomClientSessionList(gameRoom.getId());
//                if (haveInRoomPlayerList.size() == 0) {
//                    gameEvent.setEventType(PlayerEvent.ENTER);
//                } else {
//                    gameEvent.setEventType(PlayerEvent.JOIN);
//                }
//                gameEvent.setGameRoom(gameRoom);
//                gameEvent.setRoomid(gameRoom.getId());
//
//                /**
//                 * 加入房间 :1. 更新缓存中的 clientSession 2.添加userId 与 roomId 的映射关系到缓存
//                 */
//                this.joinRoom(gameRoom, clientSession, haveInRoomPlayerList);
//
//                for (ClientSession temp : haveInRoomPlayerList) {
//                    if (temp.getId().equals(clientSession.getId())) {
//                        gameEvent.setIndex(haveInRoomPlayerList.indexOf(temp));
//                        break;
//                    }
//                }
//                /**
//                 * 如果当前房间到达了最大玩家数量，则不再加入到 撮合队列
//                 */
//                if (haveInRoomPlayerList.size() < playway.getMaxPlayerNum() && needtakequene) {
//                    //未达到最大玩家数量，加入到游戏撮合 队列，继续撮合
//                    FooloxUtils.addRoom2Queue(playwayId, gameRoom);
//                }
//            }
//        }
//        return gameEvent;
//    }
//
//
//    /**
//     * 创建新房间，需要传入房间的玩法 ， 玩法定义在 系统运营后台，玩法创建后，放入系统缓存，客户端进入房间的时候，传入玩法ID参数
//     *
//     * @param playway
//     * @param userid
//     * @param cardroom
//     * @param fooloxClient
//     * @return
//     */
//    private GameRoom createGameRoom(PlaywayMeta playway, String userid, boolean cardroom, FooloxClient fooloxClient) {
//        GameRoom gameRoom = new GameRoom();
//        /**
//         * 产生房间ID，麻烦的是需要处理冲突 ，准备采用的算法是 先生成一个号码池子，然后从分布是缓存的 Queue里获取
//         */
//        gameRoom.setId(FooloxUtils.getRandomNumberChar(6));
//        gameRoom.getBaseInfo().setCreatetime(new Date());
//        gameRoom.getBaseInfo().setUpdatetime(new Date());
//
//        if (playway != null) {
//            gameRoom.getPlaywayInfo().setPlaywayId(playway.getId());
//            gameRoom.getPlaywayInfo().setRoomtype(playway.getRoomtype());
//            gameRoom.getPlaywayInfo().setMaxPlayerNum(playway.getMaxPlayerNum());
//            gameRoom.getPlaywayInfo().setMaxPlayerNum(playway.getMaxPlayerNum());
//            gameRoom.getPlaywayInfo().setCardsnum(playway.getCardsNum());
//            //局数
//            gameRoom.getPlaywayInfo().setNumofgames(playway.getNumOfGames());
//        }
//
//
//        gameRoom.getBaseInfo().setCurpalyers(1);
////        gameRoom.getBaseInfo().setCardroom(cardroom);
//        gameRoom.getBaseInfo().setStatus(RoomStatus.CRERATED);
//        gameRoom.getPlaywayInfo().setCurrentnum(0);
//        gameRoom.getBaseInfo().setCreator(userid);
//        gameRoom.getBaseInfo().setMaster(userid);
//
//
//        /**
//         * 房卡模式启动游戏
//         */
//        if (RoomType.FEE == fooloxClient.getRoomType()) {
//            gameRoom.getPlaywayInfo().setRoomtype(RoomType.FEE);
////            gameRoom.getBaseInfo().setCardroom(true);
//            gameRoom.setExtparams(fooloxClient.getExtparams());
//            /**
//             * 分配房间号码 ， 并且，启用 规则引擎，对房间信息进行赋值
//             */
//            kieSession.insert(gameRoom);
//            //执行规则，如果房间类型为地主类型，则修改变量
//            kieSession.fireAllRules();
//        } else {
//            gameRoom.getPlaywayInfo().setRoomtype(RoomType.MATCH);
//        }
//
//        //未达到最大玩家数量，加入到游戏撮合 队列，继续撮合
//        FooloxUtils.addRoom2Queue(playway.getId(), gameRoom);
//        //保存到数据库
//        FooloxUtils.published(gameRoom, FooloxDataContext.getApplicationContext().getBean(GameRoomRepository.class));
//
//        return gameRoom;
//    }
//
//    /**
//     * 玩家加入房间
//     *
//     * @param gameRoom
//     * @param clientSession
//     * @param clientSessionList
//     */
//    public void joinRoom(GameRoom gameRoom, ClientSession clientSession, List<ClientSession> clientSessionList) {
//        boolean inroom = false;
//        //已经在房间中的用户
//        for (ClientSession session : clientSessionList) {
//            if (session.getUserId().equals(clientSession.getUserId())) {
//                inroom = true;
//                break;
//            }
//        }
//        if (!inroom) {
//            clientSession.setPlayerindex(System.currentTimeMillis());
//            clientSession.setPlayerStatus(PlayerStatus.LOGIN);
//            clientSession.setPlayerType(PlayerType.NORMAL);
//            clientSession.setRoomId(gameRoom.getId());
//            clientSession.setRoomready(false);
//            clientSessionList.add(clientSession);
//            //什么也没做
//            FooloxClientContext.getFooloxClientCache().joinRoom(clientSession.getId(), gameRoom.getId());
//            //更新clientSession到缓存
//            FooloxUtils.setClientSessionById(clientSession.getUserId(), clientSession);
//        }
//
//        /**
//         *	不管状态如何，玩家一定会加入到这个房间
//         */
//        FooloxUtils.setRoomIdByUserId(clientSession.getUserId(), gameRoom.getId());
//    }
//
//    /**
//     * 抢庄
//     *
//     * @param roomId
//     * @param userId
//     * @param accept 是否抢庄
//     * @return
//     */
//    public void actionRequest(String roomId, String userId, boolean accept) {
//        GameRoom gameRoom = FooloxUtils.getRoomById(roomId);
//        if (gameRoom != null) {
//            DiZhuBoard board = FooloxUtils.getBoardByRoomId(gameRoom.getId(), DiZhuBoard.class);
//            GamePlayer player = board.getGamePlayer(userId);
//            board = ActionTaskUtils.doCatch(board, player, accept);
//
//            ActionTaskUtils.sendEvent(Command.CATCH_RESULT, new GameBoard(player.getPlayuserId(), player.isAccept(), board.isDocatch(), board.getRatio()), gameRoom);
//            GameUtils.getGame(gameRoom.getPlaywayInfo().getPlaywayId()).change(gameRoom, PlayerEvent.AUTO, 15);    //通知状态机 , 继续执行
//            //更新board 信息到缓存
//            FooloxUtils.setBoardByRoomId(gameRoom.getId(), board);
//            //1秒后开始执行任务
//            FooloxGameTaskUtil.getExpireCache().put(gameRoom.getId(), ActionTaskUtils.createAutoTask(1, gameRoom));
//        }
//    }
//
//    /**
//     * 提示出牌
//     *
//     * @param roomId
//     * @param clientSession
//     * @param cardtips      玩家的手牌
//     */
//    public void cardTips(String roomId, ClientSession clientSession, String cardtips) {
//        GameRoom gameRoom = FooloxUtils.getRoomById(roomId);
//        if (gameRoom != null) {
//            DiZhuBoard board = FooloxUtils.getBoardByRoomId(gameRoom.getId(), DiZhuBoard.class);
//            GamePlayer player = board.getGamePlayer(clientSession.getId());
//
//            TakeCards takeCards = null;
//
//            if (!StringUtils.isBlank(cardtips)) {
//                String[] cards = cardtips.split(",");
//                byte[] tipCards = new byte[cards.length];
//                for (int i = 0; i < cards.length; i++) {
//                    tipCards[i] = Byte.parseByte(cards[i]);
//                }
//                //从给出的牌中选出最小牌
//                takeCards = board.cardtip(player, board.getCardTips(player, tipCards));
//            }
//            if (takeCards == null || takeCards.getCards() == null) {
//                if (board.getLast() != null && !board.getLast().getUserid().equals(player.getPlayuserId())) {    //当前无出牌信息，刚开始出牌，或者出牌无玩家 压
//                    takeCards = board.cardtip(player, board.getLast());
//                } else {
//                    takeCards = board.cardtip(player, null);
//                }
//            }
//
//            if (takeCards.getCards() == null) {
//                takeCards.setAllow(false);    //没有 管的起的牌
//            }
//            ActionTaskUtils.sendEvent(Command.CARD_TIPS, takeCards, gameRoom);
//        }
//    }
//
//    /**
//     * 出牌，并校验出牌是否合规
//     *
//     * @param roomId
//     * @param auto   是否自动出牌，超时/托管/AI会调用 = true
//     * @return
//     */
//    public TakeCards takeCardsRequest(String roomId, String userId, boolean auto, byte[] playCards) {
//        TakeCards takeCards = null;
//        GameRoom gameRoom = FooloxUtils.getRoomById(roomId);
//        if (gameRoom != null) {
//            Board board = FooloxUtils.getBoardByRoomId(gameRoom.getId(), Board.class);
//            if (board != null) {
//                GamePlayer player = board.getGamePlayer(userId);
//                if (board.getNextplayer() != null && player.getPlayuserId().equals(board.getNextplayer().getNextplayer()) && board.getNextplayer().isTakecard() == false) {
//                    takeCards = board.takeCardsRequest(gameRoom, board, player, auto, playCards);
//                }
//            }
//        }
//        return takeCards;
//    }
//
//    /**
//     * 出牌，并校验出牌是否合规
//     *
//     * @param roomId
//     * @param userId
//     * @return
//     */
//    public SelectColor selectColorRequest(String roomId, String userId, String color) {
//        SelectColor selectColor = null;
//        GameRoom gameRoom = FooloxUtils.getRoomById(roomId);
//        if (gameRoom != null) {
//            Board board = FooloxUtils.getBoardByRoomId(gameRoom.getId(), Board.class);
//            if (board != null) {
//                //超时了 ， 执行自动出牌
////				Player[] players = board.getPlayers() ;
//                /**
//                 * 检查是否所有玩家都已经选择完毕 ， 如果所有人都选择完毕，即可开始
//                 */
//                selectColor = new SelectColor(board.getBanker());
//                if (!StringUtils.isBlank(color)) {
//                    if (!StringUtils.isBlank(color) && color.matches("[0-2]{1}")) {
//                        selectColor.setColor(Integer.parseInt(color));
//                    } else {
//                        selectColor.setColor(0);
//                    }
//                    selectColor.setTime(System.currentTimeMillis());
//                    selectColor.setCommand(Command.SELECT_RESULT);
//
//                    selectColor.setUserId(userId);
//                }
//                boolean allselected = true;
//                for (GamePlayer ply : board.getGamePlayers()) {
//                    if (ply.getPlayuserId().equals(userId)) {
//                        if (!StringUtils.isBlank(color) && color.matches("[0-2]{1}")) {
//                            ply.setColor(Integer.parseInt(color));
//                        } else {
//                            ply.setColor(0);
//                        }
//                        ply.setSelected(true);
//                    }
//                    if (!ply.isSelected()) {
//                        allselected = false;
//                    }
//                }
//                FooloxUtils.setBoardByRoomId(gameRoom.getId(), board);   //更新缓存数据
//                ActionTaskUtils.sendEvent(Command.SELECT_RESULT, selectColor, gameRoom);
//                /**
//                 * 检查是否全部都已经 定缺， 如果已全部定缺， 则发送 开打
//                 */
//                if (allselected) {
//                    /**
//                     * 重置计时器，立即执行
//                     */
//                    FooloxGameTaskUtil.getExpireCache().put(gameRoom.getId(), new CreateMJRaiseHandsTask(1, gameRoom));
//                    GameUtils.getGame(gameRoom.getPlaywayInfo().getPlaywayId()).change(gameRoom, PlayerEvent.RAISEHANDS, 0);
//                }
//            }
//        }
//        return selectColor;
//    }
//
//    /**
//     * 麻将 ， 杠碰吃胡过
//     *
//     * @param roomId
//     * @param userid
//     * @return
//     */
//    public ActionEvent actionEventRequest(String roomId, String userid, String action) {
//        ActionEvent actionEvent = null;
//        GameRoom gameRoom = FooloxUtils.getRoomById(roomId);
//        if (gameRoom != null) {
//            Board board = FooloxUtils.getBoardByRoomId(gameRoom.getId(), Board.class);
//            if (board != null) {
//                GamePlayer player = board.getGamePlayer(userid);
//                byte card = board.getLast().getCard();
//                actionEvent = new ActionEvent(board.getBanker(), userid, card, action);
//                if (!StringUtils.isBlank(action) && action.equals(MJAction.GUO.toString())) {
//                    /**
//                     * 用户动作，选择 了 过， 下一个玩家直接开始抓牌
//                     * bug，待修复：如果有多个玩家可以碰，则一个碰了，其他玩家就无法操作了
//                     */
//                    board.dealRequest(gameRoom, board, false, null);
//                } else if (!StringUtils.isBlank(action) && action.equals(MJAction.PENG.toString()) && allowAction(card, player.getActions(), MJAction.PENG.toString())) {
//                    Action playerAction = new Action(userid, action, card);
//
//                    int color = card / 36;
//                    int value = card % 36 / 4;
//                    List<Byte> otherCardList = new ArrayList<Byte>();
//                    for (int i = 0; i < player.getCards().length; i++) {
//                        if (player.getCards()[i] / 36 == color && (player.getCards()[i] % 36) / 4 == value) {
//                            continue;
//                        }
//                        otherCardList.add(player.getCards()[i]);
//                    }
//                    byte[] otherCards = new byte[otherCardList.size()];
//                    for (int i = 0; i < otherCardList.size(); i++) {
//                        otherCards[i] = otherCardList.get(i);
//                    }
//                    player.setCards(otherCards);
//                    player.getActions().add(playerAction);
//
//                    board.setNextplayer(new NextPlayer(userid, false));
//
//                    actionEvent.setTarget(board.getLast().getUserid());
//                    ActionTaskUtils.sendEvent(Command.SELECT_ACTION, actionEvent, gameRoom);
//
//                    FooloxUtils.setBoardByRoomId(gameRoom.getId(), board);   //更新缓存数据
//
//                    board.playcards(board, gameRoom, player);
//
//                } else if (!StringUtils.isBlank(action) && action.equals(MJAction.GANG.toString()) && allowAction(card, player.getActions(), MJAction.GANG.toString())) {
//                    if (board.getNextplayer().getNextplayer().equals(userid)) {
//                        card = GameUtils.getGangCard(player.getCards());
//                        actionEvent = new ActionEvent(board.getBanker(), userid, card, action);
//                        actionEvent.setActype(MJAction.AN_GANG.toString());
//                    } else {
//                        actionEvent.setActype(MJAction.MING_GANG.toString());    //还需要进一步区分一下是否 弯杠
//                    }
//                    /**
//                     * 检查是否有弯杠
//                     */
//                    Action playerAction = new Action(userid, action, card);
//                    for (Action ac : player.getActions()) {
//                        if (ac.getCard() == card && ac.getAction().equals(MJAction.PENG.toString())) {
//                            ac.setGang(true);
//                            ac.setType(MJAction.WAN_GANG.toString());
//                            playerAction = ac;
//                            break;
//                        }
//                    }
//                    int color = card / 36;
//                    int value = card % 36 / 4;
//                    List<Byte> otherCardList = new ArrayList<Byte>();
//                    for (int i = 0; i < player.getCards().length; i++) {
//                        if (player.getCards()[i] / 36 == color && (player.getCards()[i] % 36) / 4 == value) {
//                            continue;
//                        }
//                        otherCardList.add(player.getCards()[i]);
//                    }
//                    byte[] otherCards = new byte[otherCardList.size()];
//                    for (int i = 0; i < otherCardList.size(); i++) {
//                        otherCards[i] = otherCardList.get(i);
//                    }
//                    player.setCards(otherCards);
//                    player.getActions().add(playerAction);
//
//                    actionEvent.setTarget("all");    //只有明杠 是 其他人打出的 ， target 是单一对象
//
//                    ActionTaskUtils.sendEvent(Command.SELECT_ACTION, actionEvent, gameRoom);
//
//                    /**
//                     * 杠了以后， 从 当前 牌的 最后一张开始抓牌
//                     */
//                    board.dealRequest(gameRoom, board, true, userid);
//                } else if (!StringUtils.isBlank(action) && action.equals(MJAction.HU.toString())) {    //判断下是不是 真的胡了 ，避免外挂乱发的数据
//                    Action playerAction = new Action(userid, action, card);
//                    player.getActions().add(playerAction);
//                    PlaywayMeta playway = FooloxUtils.getGamePlaywayById(gameRoom.getPlaywayInfo().getPlaywayId());
//                    /**
//                     * 不同的胡牌方式，处理流程不同，推倒胡，直接进入结束牌局 ， 血战：当前玩家结束牌局，血流：继续进行，下一个玩家
//                     */
//                    if (playway.getWintype().equals(MJWinType.TUI.toString())) {        //推倒胡
//                        GameUtils.getGame(gameRoom.getPlaywayInfo().getPlaywayId()).change(gameRoom, PlayerEvent.ALLCARDS, 0);    //打完牌了,通知结算
//                    } else { //血战到底
//                        if (playway.getWintype().equals(MJWinType.END.toString())) {        //标记当前玩家的状态 是 已结束
//                            player.setEnd(true);
//                        }
//                        player.setHu(true);    //标记已经胡了
//                        /**
//                         * 当前 Player打上标记，已经胡牌了，杠碰吃就不会再有了
//                         */
//                        /**
//                         * 下一个玩家出牌
//                         */
//                        player = board.nextPlayer(board.index(player.getPlayuserId()));
//                        /**
//                         * 记录胡牌的相关信息，推倒胡 | 血战 | 血流
//                         */
//                        board.setNextplayer(new NextPlayer(player.getPlayuserId(), false));
//
//                        actionEvent.setTarget(board.getLast().getUserid());
//                        /**
//                         * 用于客户端播放 胡牌的 动画 ， 点胡 和 自摸 ，播放不同的动画效果
//                         */
//                        ActionTaskUtils.sendEvent(Command.SELECT_ACTION, actionEvent, gameRoom);
//                        FooloxUtils.setBoardByRoomId(gameRoom.getId(), board);   //更新缓存数据
//
//                        /**
//                         * 杠了以后， 从 当前 牌的 最后一张开始抓牌
//                         */
//                        board.dealRequest(gameRoom, board, true, player.getPlayuserId());
//                    }
//                }
//            }
//        }
//        return actionEvent;
//    }
//
//    /**
//     * 游戏开局
//     *
//     * @param roomId
//     * @param clientSession
//     * @param opendeal      是否同意开局
//     */
//    public void startGameRequest(String roomId, ClientSession clientSession, boolean opendeal) {
//        GameRoom gameRoom = FooloxUtils.getRoomById(roomId);
//        if (gameRoom != null) {
//            clientSession.setRoomready(true);
//            if (opendeal) {
//                clientSession.setOpendeal(true);
//            }
//            FooloxUtils.addRoomClientSession(roomId, clientSession);
//            ActionTaskUtils.roomReady(gameRoom, GameUtils.getGame(gameRoom.getPlaywayInfo().getPlaywayId()));
//
//            FooloxUtils.published(clientSession, FooloxDataContext.getApplicationContext().getBean(ClientSessionRepository.class));
//            ActionTaskUtils.sendEvent(clientSession.getId(), new Playeready(clientSession.getId(), Command.PLAYER_READY));
//        }
//    }
//
//    /**
//     * 检查是否所有玩家 都已经处于就绪状态，如果所有玩家都点击了 继续开始游戏，则发送一个 ALL事件，继续游戏，
//     * 否则，等待10秒时间，到期后如果玩家还没有就绪，就将该玩家T出去，等待新玩家加入
//     *
//     * @param roomId
//     * @param clientSession
//     * @param fooloxClient
//     * @param opendeal
//     */
//    public void restartRequest(String roomId, ClientSession clientSession, FooloxClient fooloxClient, boolean opendeal) {
//        boolean notReady = false;
//        List<ClientSession> clientSessionList = null;
//        GameRoom gameRoom = null;
//        if (!StringUtils.isBlank(roomId)) {
//            gameRoom = FooloxUtils.getRoomById(roomId);
//            clientSessionList = FooloxUtils.getRoomClientSessionList(roomId);
//            if (clientSessionList != null && clientSessionList.size() > 0) {
//                /**
//                 * 有一个 等待
//                 */
//                for (ClientSession session : clientSessionList) {
//                    if (!session.isRoomready()) {
//                        notReady = true;
//                        break;
//                    }
//                }
//            }
//        }
//        if (notReady && gameRoom != null) {
//            /**
//             * 需要增加一个状态机的触发事件：等待其他人就绪，超过5秒以后未就绪的，直接踢掉，然后等待机器人加入
//             */
//            this.startGameRequest(roomId, clientSession, opendeal);
//        } else if (clientSessionList == null || clientSessionList.size() == 0 || gameRoom == null) {//房间已解散
//            gameRequest(clientSession.getUserId(), fooloxClient.getPlaywayId(), fooloxClient, clientSession);
//            /**
//             * 结算后重新开始游戏
//             */
//            clientSession.setRoomready(true);
//            FooloxUtils.addRoomClientSession(roomId, clientSession);
//        }
//    }
//
//    /**
//     * 结束 当前牌局
//     *
//     * @param roomid
//     * @return
//     */
//    public void finished(String roomid) {
//        if (!StringUtils.isBlank(roomid)) {
//            //从任务缓存中 清除 room相关的task
//            FooloxGameTaskUtil.getExpireCache().remove(roomid);
//            //删除room 相关的 board 信息
//            FooloxUtils.delBoardByRoomId(roomid);
//        }
//    }
//
//    /**
//     * 为防止同步数据错误，校验是否允许刚碰牌
//     *
//     * @param card
//     * @param actions
//     * @return
//     */
//    private boolean allowAction(byte card, List<Action> actions, String actiontype) {
//        int take_color = card / 36;
//        int take_value = card % 36 / 4;
//        boolean allow = true;
//        for (Action action : actions) {
//            int color = action.getCard() / 36;
//            int value = action.getCard() % 36 / 4;
//            if (take_color == color && take_value == value && action.getAction().equals(actiontype)) {
//                allow = false;
//                break;
//            }
//        }
//        return allow;
//    }

}
