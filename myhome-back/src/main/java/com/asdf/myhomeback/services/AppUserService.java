package com.asdf.myhomeback.services;

import com.asdf.myhomeback.Exception.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AppUserService extends UserDetailsService {

    void register(RegistrationDTO registrationDTO) throws AppUserException, IOException;

    void verify(String username) throws AppUserException;

}
