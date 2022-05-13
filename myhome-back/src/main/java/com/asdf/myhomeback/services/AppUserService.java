package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.RegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    void register(RegistrationDTO registrationDTO);

}
