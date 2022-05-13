package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.Exception.AppUserException;
import com.asdf.myhomeback.models.AppUser;

import java.util.regex.Pattern;

public class AppUserUtils {

    public static void checkBasicAppUserInfo(AppUser appUser) throws AppUserException {
        if(appUser.getUsername() == null || appUser.getUsername().length() < 3)
            throw new AppUserException("Invalid username. Should have minimum 3 characters.");
        if(appUser.getPassword() == null || !PasswordValidator.isValid(appUser.getPassword()))
            throw new AppUserException("""
                    Invalid password.
                    Password must contain at least one digit [0-9].
                    Password must contain at least one lowercase Latin character [a-z].
                    Password must contain at least one uppercase Latin character [A-Z].
                    Password must contain at least one special character like ! @ # & ( ).
                    Password must contain a length of at least 8 characters and a maximum of 20 characters.""");
        if(appUser.getEmail() == null || !isEmailValid(appUser.getEmail()))
            throw new AppUserException("Invalid email.");
        if(appUser.getFirstname() == null || appUser.getFirstname().length() < 3)
            throw new AppUserException("Invalid firstname. Should have minimum 3 characters.");
        if(appUser.getLastname() == null || appUser.getLastname().length() < 3)
            throw new AppUserException("Invalid lastname. Should have minimum 3 characters.");
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
