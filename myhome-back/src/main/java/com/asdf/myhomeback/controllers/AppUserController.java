package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.AppUserDTO;
import com.asdf.myhomeback.controllers.handlers.CustomLoginFailureHandler;
import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import com.asdf.myhomeback.dto.UserTokenStateDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.security.auth.JwtAuthenticationRequest;
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.BlacklistedTokenService;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.utils.AppUserUtils;
import com.asdf.myhomeback.utils.ControllerUtils;
import com.asdf.myhomeback.utils.LogMessGen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class AppUserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private BlacklistedTokenService blacklistedTokenService;

    @Autowired
    private LogService logService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletRequest  request)  {
        if (appUserService.checkMaliciousIpAddress(request.getRemoteAddr())) {
            logService.generateErrLog(LogMessGen.maliciousIpAddress(request.getRemoteAddr()));
            return new ResponseEntity<>(new UserTokenStateDTO("Your address is on list of malicious IP addresses."), HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = null;
        try {
            AppUserUtils.checkLoginUserInfo(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            try {
                String exception = customLoginFailureHandler.onAuthenticationFailure(authenticationRequest.getUsername());
                logService.generateErrLog(LogMessGen.badCredentialLogin(authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenStateDTO(exception), HttpStatus.UNAUTHORIZED);
            } catch (UsernameNotFoundException ex) {
                logService.generateErrLog(LogMessGen.userNotFoundLogin(authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenStateDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (LockedException exp) {
                logService.generateErrLog(LogMessGen.accountLockedLogin(authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenStateDTO(exp.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        } catch (LockedException e) {
            logService.generateErrLog(LogMessGen.accountLockedLogin(authenticationRequest.getUsername()));
            String exception = "Your account has been locked. Contact administrator for more details.";
            return new ResponseEntity<>(new UserTokenStateDTO(exception), HttpStatus.UNAUTHORIZED);
        } catch (AppUserException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(authenticationRequest.getUsername(), e.getMessage()));
            return new ResponseEntity<>(new UserTokenStateDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(authenticationRequest.getUsername()), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        AppUser appUser = (AppUser) authentication.getPrincipal();

        if(!appUser.isVerified()) {
            logService.generateErrLog(LogMessGen.accountUnverifiedLogin(authenticationRequest.getUsername()));
            return new ResponseEntity<>(new UserTokenStateDTO("You must verified your account before login."), HttpStatus.FORBIDDEN);
        }

        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(appUser.getUsername(), appUser.getRoles(), fingerprint);
        int expiresIn = tokenUtils.getExpiredIn();

        if (appUser.getFailedAttempt() > 0) {
            appUserService.resetFailedAttempts(appUser.getUsername());
        }

//        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";
        String cookie = "__Secure-Fgp=" + fingerprint + "; SameSite=Strict; HttpOnly; Path=/; Secure";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);

        logService.generateInfoLog(LogMessGen.successfulLogin(appUser.getUsername()));
        return ResponseEntity.ok().headers(headers).body(new UserTokenStateDTO(jwt, expiresIn));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request)  {
        String authToken = tokenUtils.getToken(request);
        blacklistedTokenService.saveToken(authToken);

        String username = tokenUtils.getUsernameFromToken(authToken);
        logService.generateInfoLog(LogMessGen.successfulLogout(username));
        return new ResponseEntity<>("You have been successfully logged out!", HttpStatus.OK);
    }

    @GetMapping(value = "/getAllUsersButAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_USERS_WITHOUT_ADMIN')")
    public ResponseEntity<AppUserDTO[]> getAllUsersButAdmin(Pageable pageable) {
        Page<AppUser> users = appUserService.getAllUsersButAdmin(pageable);

        return new ResponseEntity<>(users.stream().map(AppUserDTO::new).toArray(AppUserDTO[]::new),
                ControllerUtils.createPageHeaderAttributes(users), HttpStatus.OK);
    }

    @GetMapping(value = "/searchUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SEARCH_USERS')")
    public ResponseEntity<AppUserDTO[]> searchUsers(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "verified", required = false) String verified,
            @RequestParam(value = "locked", required = false) String locked,
            Pageable pageable,
            HttpServletRequest request) {

        try {
            Page<AppUser> users = appUserService.searchUsers(searchField, userType, verified, locked, pageable);
            return new ResponseEntity<>(users.stream().map(AppUserDTO::new).toArray(AppUserDTO[]::new),
                    ControllerUtils.createPageHeaderAttributes(users), HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(request);
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            String username = tokenUtils.getUsernameFromRequest(request);
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id, HttpServletRequest request){
        String username = tokenUtils.getUsernameFromRequest(request);
        try{
            AppUser user = appUserService.deleteUser(id);
            logService.generateInfoLog(LogMessGen.successfulDeleteUser(username, user.getUsername()));
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unknown error happened while deleting user!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/unlockUser")
    @PreAuthorize("hasAuthority('UNLOCK_USER')")
    public ResponseEntity<String> unlockUser(@RequestBody Long id, HttpServletRequest request){
        String username = tokenUtils.getUsernameFromRequest(request);
        try{
            AppUser appUser = appUserService.unlockUser(id);
            logService.generateInfoLog(LogMessGen.successfulUnlockUser(username, appUser.getUsername()));
            return new ResponseEntity<>("User unlocked successfully", HttpStatus.OK);
        } catch (AppUserException e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, e.getMessage()));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(username), Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>("Unknown error happened while unlocking user!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO registrationDTO) {
        try {
            appUserService.register(registrationDTO);
            logService.generateInfoLog(LogMessGen.successfulRegistration(registrationDTO.getUsername()));
            return new ResponseEntity<>("Registration successfully finished. Check your email to verify it.", HttpStatus.OK);
        } catch (AppUserException ex) {
            ex.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessRegistration(registrationDTO.getUsername(), ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(), Arrays.toString(ex.getStackTrace()));
            return new ResponseEntity<>("There was an unknown error with your registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verify-registration/{username}")
    public ResponseEntity<String> verifyRegistration(@PathVariable String username) {
        try {
            appUserService.verify(username);
            logService.generateInfoLog(LogMessGen.successfulAccVerification(username));
            return new ResponseEntity<>("Registration successfully verified.", HttpStatus.OK);
        } catch (AppUserException ex) {
            ex.printStackTrace();
            logService.generateErrLog(LogMessGen.exMessUser(username, ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            logService.generateErrLog(LogMessGen.internalServerError(), Arrays.toString(ex.getStackTrace()));
            return new ResponseEntity<>("There was an unknown error while verifying registration.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
