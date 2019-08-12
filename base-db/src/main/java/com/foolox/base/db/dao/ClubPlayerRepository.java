package com.foolox.base.db.dao;

import com.foolox.base.db.domain.ClubPlayer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 03/08/2019
 */
@Repository
public interface ClubPlayerRepository extends CrudRepository<ClubPlayer, Long> {

    @Query(value = "select t from ClubPlayer t where t.playerId = :playerId and t.clubId= :clubId")
    ClubPlayer findByClubIdAndPlayerId(@Param("playerId") long playerId, @Param("clubId")long clubId);
}
