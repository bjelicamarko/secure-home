package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.controllers.handlers.CustomLoginFailureHandler;
import com.asdf.myhomeback.dto.UserTokenStateDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.security.auth.JwtAuthenticationRequest;
import com.asdf.myhomeback.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)  {
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            String exception = customLoginFailureHandler.onAuthenticationFailure(authenticationRequest.getUsername());
            return new ResponseEntity<>(new UserTokenStateDTO(exception), HttpStatus.UNAUTHORIZED);
        } catch (LockedException e) {
            String exception = "Your account has been locked. Contact administrator for more details.";
            return new ResponseEntity<>(new UserTokenStateDTO(exception), HttpStatus.UNAUTHORIZED);
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

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }

}
