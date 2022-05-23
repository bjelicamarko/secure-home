package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.models.AppUser;

import java.io.IOException;

import static com.asdf.myhomeback.utils.BasicValidator.*;
import static com.asdf.myhomeback.utils.PasswordValidator.*;

public class AppUserUtils {

    public static void checkBasicAppUserInfo(AppUser appUser) throws AppUserException, IOException {
        if(!isValidUsername(appUser.getUsername()))
            throw new AppUserException("Invalid username. None other than latin characters and digits, length: [3-20].");
        if(appUser.getPassword() == null || !isPasswordValid(appUser.getPassword()))
            throw new AppUserException("""
                    Invalid password.
                    Password must contain at least one digit [0-9].
                    Password must contain at least one lowercase Latin character [a-z].
                    Password must contain at least one uppercase Latin character [A-Z].
                    Password must contain at least one special character ! @ # & ( ) - + = < >.
                    Password must contain a length of at least 8 characters and a maximum of 20 characters."""); // Proveri sifru
        if(PasswordValidator.isCommon(appUser.getPassword()))
            throw new AppUserException("Common password. Try another one");
        if(appUser.getEmail() == null || !isEmailValid(appUser.getEmail()))
            throw new AppUserException("Invalid email.");
        if(!isValidString(appUser.getFirstname()))
            throw new AppUserException("Invalid firstname. None other than latin characters, length: [3-20].");
        if(!isValidString(appUser.getLastname()))
            throw new AppUserException("Invalid lastname. None other than latin characters, length: [3-20].");
    }

    public static void checkLoginUserInfo(String username, String password) throws AppUserException {
        if(!isValidUsername(username))
            throw new AppUserException("Invalid username. None other than latin characters and digits, length: [3-20].");
        if(!isPasswordValidForLogin(password))
            throw new AppUserException("Invalid password. None other than latin characters, digits and [! @ # & ( ) - + = < >], length: [8-20].");
    }

    public static void checkSearchField(String searchField) throws AppUserException {
        if(!isValidSearchField(searchField))
            throw new AppUserException("Invalid username in search field. None other than latin characters and digits.");
    }

    public static void checkUserType(String userType) throws AppUserException {
        if(!(userType.equals("ROLE_BOTH") || userType.equals("ROLE_UNASSIGNED") || userType.equals("ROLE_TENANT") ||
           userType.equals("ROLE_OWNER") || userType.equals("")))
            throw new AppUserException("Invalid user type.");
    }

    public static void checkVerifiedOrLocked(String field) throws AppUserException {
        if(!(field.equals("true") || field.equals("false") || field.equals("")))
            throw new AppUserException("Invalid field.");
    }
}
