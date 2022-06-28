package com.asdf.adminback.util;

import com.asdf.adminback.exceptions.AppUserException;

import java.util.regex.Pattern;

public class AppUserUtils {

    public static void checkLoginUserInfo(String username, String password) throws AppUserException {
        if(!isValidUsername(username))
            throw new AppUserException("Invalid username. None other than latin characters and digits, length: [3-20].");
        if(!isPasswordValidForLogin(password))
            throw new AppUserException("Invalid password. None other than latin characters, digits and [! @ # & ( ) - + = < >], length: [8-20].");
    }

    public static boolean isValidUsername(String username){
        String reg = "^[a-zA-Z0-9]{3,20}$";
        Pattern pat = Pattern.compile(reg);

        if(username == null)
            return false;

        return pat.matcher(username).matches();
    }

    public static boolean isPasswordValidForLogin(String password){
        String reg = "^[a-zA-Z0-9!@#&()\\-+=<>]{8,20}$";
        Pattern pat = Pattern.compile(reg);

        if(password == null)
            return false;

        return pat.matcher(password).matches();
    }

}
