package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
