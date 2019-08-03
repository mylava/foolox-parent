package com.foolox.base.db.dao;

import com.foolox.base.db.domain.Asset;
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
public interface AssetRepository extends CrudRepository<Asset,Long> {

    @Query(value = "select t from Asset t where t.playerId = :playerId and t.assetType= :assetType")
    Asset findByPlayerId(@Param("playerId") long playerId, @Param("assetType")int assetType);
}
