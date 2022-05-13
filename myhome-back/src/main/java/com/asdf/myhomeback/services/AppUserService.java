package com.asdf.myhomeback.services;

import com.asdf.myhomeback.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    Page<AppUser> getAllUsersButAdmin(Pageable pageable);

    Page<AppUser> searchUsers(String searchField, String userType, Pageable pageable);

}
