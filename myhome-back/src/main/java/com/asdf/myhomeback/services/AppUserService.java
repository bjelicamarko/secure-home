package com.asdf.myhomeback.services;

import com.asdf.myhomeback.Exception.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    void register(RegistrationDTO registrationDTO) throws AppUserException;

    void verify(String username) throws AppUserException;

}
