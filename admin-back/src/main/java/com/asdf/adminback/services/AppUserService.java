package com.asdf.adminback.services;

import com.asdf.adminback.exceptions.AppUserException;
import com.asdf.adminback.models.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    int MAX_FAILED_ATTEMPTS = 3;

    long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    AppUser unlockUser(Long id) throws AppUserException;

    void increaseFailedAttempts(AppUser user);

    void lock(AppUser user);

    void resetFailedAttempts(String username);

    boolean unlockWhenTimeExpired(AppUser user);

    boolean isUserLocked(String username);
}
