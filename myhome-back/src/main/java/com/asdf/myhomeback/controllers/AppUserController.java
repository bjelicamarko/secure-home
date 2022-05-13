package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.Exception.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import com.asdf.myhomeback.dto.UserTokenStateDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.security.auth.JwtAuthenticationRequest;
import com.asdf.myhomeback.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private AppUserService appUserService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)  {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        AppUser appUser = (AppUser) authentication.getPrincipal();

        if(!appUser.isVerified())
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String jwt = tokenUtils.generateToken(appUser.getUsername(), appUser.getRoles().get(0).getName());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO registrationDTO) {
        try {
            appUserService.register(registrationDTO);
            return new ResponseEntity<>("Registration successfully finished. Check your email to verify it.", HttpStatus.OK);
        }catch (AppUserException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("There was an unknown error with your registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verify-registration/{username}")
    public ResponseEntity<String> register(@PathVariable String username) {
        try {
            appUserService.verify(username);
            return new ResponseEntity<>("Registration successfully verified.", HttpStatus.OK);
        }catch (AppUserException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("There was an unknown error while verifying registration.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
