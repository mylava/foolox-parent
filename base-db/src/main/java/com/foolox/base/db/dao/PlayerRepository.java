package com.foolox.base.db.dao;

import com.foolox.base.db.domain.Asset;
import com.foolox.base.db.domain.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 31/07/2019
 */
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long>{

    @Query(value = "select t from Player t where t.username = :username")
    Player findByUsername(@Param("username") String username);
}
