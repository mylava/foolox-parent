package com.foolox.base.db.dao;

import com.foolox.base.db.domain.GameRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
@Repository
public interface GameRoomRepository extends CrudRepository<GameRoom,Long> {
}
