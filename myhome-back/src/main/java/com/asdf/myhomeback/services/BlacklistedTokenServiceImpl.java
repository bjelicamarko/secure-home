package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.BlacklistedToken;
import com.asdf.myhomeback.repositories.BlackListedTokenRepository;
import com.asdf.myhomeback.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class BlacklistedTokenServiceImpl implements BlacklistedTokenService {

    @Autowired
    private BlackListedTokenRepository blackListedTokenRepository;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public BlacklistedToken getBlackListedToken(String token) {
        return blackListedTokenRepository.findByToken(token);
    }

    @Override
    public void saveToken(String token) {
        blackListedTokenRepository.save(new BlacklistedToken(token));
    }

    @Override
    public void removeToken(BlacklistedToken token) {
        blackListedTokenRepository.delete(token);
    }

    @Override
    @Scheduled(cron = "0 0/15 * * * *") // every 15 minutes
    public void removeExpiredTokens() {
        List<BlacklistedToken> blacklistedTokenList = blackListedTokenRepository.findAll();
        AtomicInteger deletedTokens = new AtomicInteger();
        blacklistedTokenList.forEach(blacklistedToken -> {
            String token = blacklistedToken.getToken();
            Date expiredIn = tokenUtils.getExpirationDateFromToken(token);
            if (!expiredIn.before(new Date())) {
                removeToken(blacklistedToken);
                deletedTokens.addAndGet(1);
            }
        });
        if (deletedTokens.intValue() != 0)
            printDeletingMessage(deletedTokens);
    }

    private void printDeletingMessage(AtomicInteger deletedTokens) {
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Deleted " + deletedTokens + " expired blacklisted token/s.");
        System.out.println("-------------------------------------------------------------------------------");
    }
}