package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.BlacklistedToken;

public interface BlacklistedTokenService {

    BlacklistedToken getBlackListedToken(String token);

    void saveToken(String token);

    void removeToken(BlacklistedToken token);

    void removeExpiredTokens();
}
