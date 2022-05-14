package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    int MAX_FAILED_ATTEMPTS = 3;

    long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    Page<AppUser> getAllUsersButAdmin(Pageable pageable);

    Page<AppUser> searchUsers(String searchField, String userType, Pageable pageable);

    void deleteUser(Long id);

    void increaseFailedAttempts(AppUser user);

    void lock(AppUser user);

    void resetFailedAttempts(String username);

    boolean unlockWhenTimeExpired(AppUser user);
}
