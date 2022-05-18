package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    BlacklistedToken getByUsername(String username);
}
