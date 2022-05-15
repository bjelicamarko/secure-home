package com.asdf.myhomeback.services;

import com.asdf.myhomeback.Exception.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.models.UserRole;
import com.asdf.myhomeback.repositories.AppUserRepository;
import com.asdf.myhomeback.utils.AppUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired UserRoleService userRoleService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MailService mailService;

    public static final String LINK_ROOT = "http://localhost:8081/api/users/verify-registration/";

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
    public void register(RegistrationDTO registrationDTO) throws AppUserException, IOException {
        AppUser appUser = new AppUser(registrationDTO);

        AppUserUtils.checkBasicAppUserInfo(appUser);
        if(appUserRepository.findByUsername(appUser.getUsername()).isPresent())
            throw new AppUserException("Username already in use.");
        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent())
            throw new AppUserException("Email already in use.");

        UserRole userRole = userRoleService.findByName("ROLE_UNASSIGNED");
        appUser.getRoles().add(userRole);

        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);
        try {
            mailService.sendmail("Account verification", composeVerificationEmail(appUser.getUsername()), appUser.getEmail());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new AppUserException("Error happened while sending verification email.");
        }
    }

    private String composeVerificationEmail(String username) {
        String message = """
                Account was created using this email address with username: '%s'.
                To verify your identity click on this link %s%s

                This is an automatically generated email – please do not reply to it. 
                ©secure-home""";

        return String.format(message, username, LINK_ROOT, username);
    }

    @Override
    public void verify(String username) throws AppUserException {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if(optionalAppUser.isPresent()) {
            AppUser appUser = optionalAppUser.get();
            appUser.setVerified(true);
            appUserRepository.save(appUser);
        } else {
            throw new AppUserException("User with given username does not exists in database.");
        }
    }
}
