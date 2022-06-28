package com.asdf.adminback.services.impl;

import com.asdf.adminback.exceptions.AppUserException;
import com.asdf.adminback.models.AppUser;
import com.asdf.adminback.models.IpAddress;
import com.asdf.adminback.repositories.AppUserRepository;
import com.asdf.adminback.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private List<IpAddress> maliciousIpAddresses;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username).orElse(null);
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        else{
            return appUser;
        }
    }

    @Override
    public AppUser unlockUser(Long id) throws AppUserException {
        System.out.println(id);
        Optional<AppUser> user = appUserRepository.findByIdAndAccountNonLocked(id, false);

        if (user.isEmpty()) throw new AppUserException("Invalid user for unlocking.");

        AppUser appUser = user.get();
        appUser.setAccountNonLocked(true);
        appUser.setLockTime(null);
        appUser.setFailedAttempt(0);

        return appUserRepository.save(appUser);
    }

    @Transactional
    public void increaseFailedAttempts(AppUser user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        appUserRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
    }

    @Transactional
    public void resetFailedAttempts(String username) {
        appUserRepository.updateFailedAttempts(0, username);
    }

    public void lock(AppUser user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());

        appUserRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(AppUser user) {
        long lockTimeInMillis = user.getLockTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            appUserRepository.save(user);

            return true;
        }

        return false;
    }

    @Override
    public boolean isUserLocked(String username) {
        Optional<AppUser> user = appUserRepository.findByUsernameAndAccountNonLocked(username, false);
        return user.isEmpty();
    }

    @Override
    public boolean checkMaliciousIpAddress(String remoteAddress) {

        IpAddress ipAddress = new IpAddress(remoteAddress);


        for (IpAddress ipAddressTemp : maliciousIpAddresses) {
            if(ipAddress.getAddress().equals(ipAddressTemp.getAddress())) {
                return true;
            }
        }
        return false;
    }

}
