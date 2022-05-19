package com.asdf.myhomeback.services;

public interface BlacklistedTokenService {
    boolean isTokenBlackListed(String uuid);

    void saveToken(String uuid, String username, Long tokenExpiredIn);
}
