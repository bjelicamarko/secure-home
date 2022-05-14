package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    int MAX_FAILED_ATTEMPTS = 3;
    long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    void increaseFailedAttempts(AppUser user);

    void lock(AppUser user);

    void resetFailedAttempts(String username);

    boolean unlockWhenTimeExpired(AppUser user);
}
