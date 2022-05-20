package com.asdf.myhomeback.controllers;

import com.asdf.myhomeback.dto.AppUserDTO;
import com.asdf.myhomeback.controllers.handlers.CustomLoginFailureHandler;
import com.asdf.myhomeback.Exception.AppUserException;
import com.asdf.myhomeback.dto.RegistrationDTO;
import com.asdf.myhomeback.dto.UserTokenStateDTO;
import com.asdf.myhomeback.models.AppUser;
import com.asdf.myhomeback.security.TokenUtils;
import com.asdf.myhomeback.security.auth.JwtAuthenticationRequest;
import com.asdf.myhomeback.services.AppUserService;
import com.asdf.myhomeback.services.BlacklistedTokenService;
import com.asdf.myhomeback.util.ControllerUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

        if(!appUser.isVerified())
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        String fingerprint = tokenUtils.generateFingerprint();
        String jwt = tokenUtils.generateToken(appUser.getUsername(), appUser.getRoles().get(0).getName(), fingerprint);
        int expiresIn = tokenUtils.getExpiredIn();

        if (appUser.getFailedAttempt() > 0) {
            appUserService.resetFailedAttempts(appUser.getUsername());
        }

        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);

        return ResponseEntity.ok().headers(headers).body(new UserTokenStateDTO(jwt, expiresIn));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request)  {
        String authToken = tokenUtils.getToken(request);
        blacklistedTokenService.saveToken(authToken);
        return new ResponseEntity<>("You have been successfully logged out!", HttpStatus.OK);
    }

    @GetMapping(value = "/getAllUsersButAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('READ_USERS_WITHOUT_ADMIN')")
    public ResponseEntity<List<AppUserDTO>> getAllUsersButAdmin(Pageable pageable) {
        Page<AppUser> users = appUserService.getAllUsersButAdmin(pageable);
        return new ResponseEntity<>(users.stream().map(AppUserDTO::new).toList(),
                ControllerUtils.createPageHeaderAttributes(users), HttpStatus.OK);
    }

    @GetMapping(value = "/searchUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SEARCH_USERS')")
    public ResponseEntity<List<AppUserDTO>> searchUsers(
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "userType", required = false) String userType,
            Pageable pageable) {
        Page<AppUser> users = appUserService.searchUsers(searchField, userType, pageable);

        return new ResponseEntity<>(users.stream().map(AppUserDTO::new).toList(),
                    ControllerUtils.createPageHeaderAttributes(users), HttpStatus.OK);

    }

    @DeleteMapping(value = "/deleteUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long id){
        try{
            appUserService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error happened while deleting user!", HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<String> verifyRegistration(@PathVariable String username) {
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
