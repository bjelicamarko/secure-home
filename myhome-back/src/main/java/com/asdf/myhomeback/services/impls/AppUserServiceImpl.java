package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.models.UserRole;
import com.asdf.myhomeback.repositories.AppUserRepository;
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.UserRoleService;
import com.asdf.myhomeback.utils.AppUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneOffset;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    UserRoleService userRoleService;

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
    public Page<AppUser> getAllUsersButAdmin(Pageable pageable) {
        return appUserRepository.getAllUsersButAdmin(pageable);
    }

    @Override
    public Page<AppUser> searchUsers(String searchField, String userType, String verified, String locked,
                                     Pageable pageable) throws AppUserException {
        if (searchField == null)
            searchField = "";

        userType = this.checkAllFromField(userType);
        verified = this.checkAllFromField(verified);
        locked = this.checkAllFromField(locked);


        AppUserUtils.checkSearchField(searchField);
        AppUserUtils.checkUserType(userType);
        AppUserUtils.checkVerifiedOrLocked(verified);
        AppUserUtils.checkVerifiedOrLocked(locked);

        return appUserRepository.searchUsers(searchField, userType, verified, locked, pageable);
    }

    private String checkAllFromField(String field) {
        if (field == null){
            field = "";
        }else if (field.equalsIgnoreCase("all")){
            field = "";
        }
        return field;
    }

    @Override
    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    public AppUser deleteUser(Long id) throws AppUserException {
        Optional<AppUser> user = appUserRepository.findByIdAndDeleted(id, false);
        if (user.isEmpty()) throw new AppUserException("Invalid user for deletion.");

        AppUser appUser = user.get();
        appUser.setDeleted(true);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser unlockUser(Long id) throws AppUserException {
        System.out.println(id);
        Optional<AppUser> user = appUserRepository.findByIdVerifiedButLocked(id);

        if (user.isEmpty()) throw new AppUserException("Invalid user for unlocking.");

        AppUser appUser = user.get();
        appUser.setAccountNonLocked(true);

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
    public void register(RegistrationDTO registrationDTO) throws AppUserException, IOException {
        AppUser appUser = new AppUser(registrationDTO);

        AppUserUtils.checkBasicAppUserInfo(appUser);
        if(appUserRepository.findByUsername(appUser.getUsername()).isPresent())
            throw new AppUserException("Username already in use.");
        if(appUserRepository.findByEmail(appUser.getEmail()).isPresent())
            throw new AppUserException("Email already in use.");

        UserRole userRole = userRoleService.findByName("ROLE_UNASSIGNED");
        appUser.getRoles().add(userRole);
        appUser.setUserType(userRole.getName());

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

    @Override
    public void save(AppUser user) {
        appUserRepository.save(user);
    }

    @Override
    public AppUser findByUsernameVerifiedUnlocked(String username) {
        return appUserRepository.findByUsernameVerifiedUnlocked(username).orElse(null);
    }
}
