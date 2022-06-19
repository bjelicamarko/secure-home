package com.asdf.adminback.controllers;

import com.asdf.adminback.controllers.handlers.CustomLoginFailureHandler;
import com.asdf.adminback.dto.UserTokenStateDTO;
import com.asdf.adminback.exceptions.AppUserException;
import com.asdf.adminback.models.AppUser;
import com.asdf.adminback.security.TokenUtils;
import com.asdf.adminback.security.auth.JwtAuthenticationRequest;
import com.asdf.adminback.services.AppUserService;
import com.asdf.adminback.services.LogService;
import com.asdf.adminback.util.AppUserUtils;
import com.asdf.adminback.util.LogMessGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
    private LogService logService;

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)  {
        Authentication authentication = null;
        try {
            AppUserUtils.checkLoginUserInfo(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            try {
                e.printStackTrace();
                String exception = customLoginFailureHandler.onAuthenticationFailure(authenticationRequest.getUsername());
                logService.generateErrLog(LogMessGen.badCredentialLogin(authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenStateDTO(exception), HttpStatus.UNAUTHORIZED);
            } catch (UsernameNotFoundException ex) {
                ex.printStackTrace();
                logService.generateErrLog(LogMessGen.userNotFoundLogin(authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenStateDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
            } catch (LockedException exp) {
                exp.printStackTrace();
                logService.generateErrLog(LogMessGen.accountLockedLogin(authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenStateDTO(exp.getMessage()), HttpStatus.UNAUTHORIZED);
            }
        } catch (LockedException e) {
            e.printStackTrace();
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
        String jwt = tokenUtils.generateToken(appUser.getUsername(), appUser.getRoles().get(0).getName());
        int expiresIn = tokenUtils.getExpiredIn();

        if (appUser.getFailedAttempt() > 0) {
            appUserService.resetFailedAttempts(appUser.getUsername());
        }

        logService.generateInfoLog(LogMessGen.successfulLogin(appUser.getUsername()));

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
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
}
