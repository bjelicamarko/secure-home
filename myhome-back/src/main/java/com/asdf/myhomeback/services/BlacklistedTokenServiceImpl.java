package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.BlacklistedToken;
import com.asdf.myhomeback.repositories.BlackListedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlacklistedTokenServiceImpl implements BlacklistedTokenService {

    @Autowired
    private BlackListedTokenRepository blackListedTokenRepository;

    @Override
    public boolean isTokenBlackListed(String uuid) {
        BlacklistedToken blackListedToken = blackListedTokenRepository.getByUUID(uuid);
        if (blackListedToken != null) {
            if (blackListedToken.getExpiredIn() <= (new Date()).getTime())
                blackListedTokenRepository.delete(blackListedToken);
            return true;
        }
        return false;
    }

    @Override
    public void saveToken(String uuid, String username, Long tokenExpiredIn) {
        blackListedTokenRepository.save(new BlacklistedToken(uuid, username, tokenExpiredIn));
    }
}