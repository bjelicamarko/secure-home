package com.asdf.adminback.controllers.handlers;

import com.asdf.adminback.models.AppUser;
import com.asdf.adminback.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler {

    @Autowired
    private AppUserService appUserService;

    public String onAuthenticationFailure(String username) throws UsernameNotFoundException {
        AppUser user = (AppUser) appUserService.loadUserByUsername(username);
        String exception = "";
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < AppUserService.MAX_FAILED_ATTEMPTS - 1) {
                    appUserService.increaseFailedAttempts(user);
                    exception = "Your password is incorrect! Carefully, u have " +
                            (AppUserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt() - 1)
                            + " more " + (user.getFailedAttempt() == 2 ? "attempt" : "attempts");
                } else {
                    appUserService.lock(user);
                    throw new LockedException("Your account has been locked. Contact administrator for more details.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (appUserService.unlockWhenTimeExpired(user)) {
                    exception = "Your account has been unlocked. Please try to login again.";
                }
            }

        }
        else {
            throw new UsernameNotFoundException("Non-existent username.");
        }
        return exception;
    }
}
