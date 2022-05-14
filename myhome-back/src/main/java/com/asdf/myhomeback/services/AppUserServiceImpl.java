package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

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
    public Page<AppUser> getAllUsersButAdmin(Pageable pageable) {
        return appUserRepository.getAllUsersButAdmin(pageable);
    }

    @Override
    public Page<AppUser> searchUsers(String searchField, String userType, Pageable pageable) {
        if (searchField == null)
            searchField = "";

        if (userType == null){
            userType = "";
        }else if (userType.equalsIgnoreCase("all")){
            userType = "";
        }
        return appUserRepository.searchUsers(searchField, userType, pageable);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(id, false);
        if (user.isPresent()) {
            AppUser appUser = user.get();
            appUser.setDeleted(true);
            appUserRepository.save(appUser);
        } // else bi trebalo neka greska biti
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

}
