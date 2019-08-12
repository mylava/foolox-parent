package com.foolox.base.db.dao;

import com.foolox.base.db.domain.SysDic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 04/08/2019
 */
@Repository
public interface SysDicRepository extends CrudRepository<SysDic, Long> {
}
