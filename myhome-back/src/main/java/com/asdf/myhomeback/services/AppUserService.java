package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.io.IOException;

public interface AppUserService extends UserDetailsService {

    int MAX_FAILED_ATTEMPTS = 3;

    long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    Page<AppUser> getAllUsersButAdmin(Pageable pageable);

    Page<AppUser> searchUsers(String searchField, String userType, String verified, String locked,
                              Pageable pageable) throws AppUserException;

    AppUser getUser(Long id);

    AppUser getUser(String username);

    AppUser deleteUser(Long id) throws AppUserException;

    AppUser unlockUser(Long id) throws AppUserException;

    void increaseFailedAttempts(AppUser user);

    void lock(AppUser user);

    void resetFailedAttempts(String username);

    boolean unlockWhenTimeExpired(AppUser user);

    void register(RegistrationDTO registrationDTO) throws AppUserException, IOException;

    void verify(String username) throws AppUserException;

    void save(AppUser user);

    AppUser findByUsernameVerifiedUnlocked(String username);

    boolean checkMaliciousIpAddress(String remoteAddress);

    boolean isUserLocked(String username);
}
