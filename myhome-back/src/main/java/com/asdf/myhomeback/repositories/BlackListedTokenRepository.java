package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    @Query("select t from BlacklistedToken t where t.uuid = ?1")
    BlacklistedToken getByUUID(String uuid);
}
