package com.asdf.myhomeback.services;

public interface BlacklistedTokenService {
    boolean isTokenBlackListed(String username);

    void saveToken(String username, Long tokenExpiredIn);
}
