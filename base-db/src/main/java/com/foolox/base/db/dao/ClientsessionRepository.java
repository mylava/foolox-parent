package com.foolox.base.db.dao;

import com.foolox.base.db.domain.ClientSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 29/07/2019
 */
@Repository
public interface ClientsessionRepository extends MongoRepository<ClientSession, String> {
}
