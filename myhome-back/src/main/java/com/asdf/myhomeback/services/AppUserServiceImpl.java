package com.asdf.myhomeback.services;

import com.asdf.myhomeback.dto.RegistrationDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public void register(RegistrationDTO registrationDTO) {
        AppUser appUser = new AppUser(registrationDTO);
        // Dodati hes i salt za sifru
        // Dodati ulogu
        // Pri autentifikaciji potrebno je izmeniti mehanizam provere sifre jer ce biti posoljena
    }
}
