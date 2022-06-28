package com.asdf.myhomeback.utils;

import java.util.regex.Pattern;

public class BasicValidator {

    public static boolean isValidString(String text){
        String reg = "^[a-zA-Z]{3,20}$";
        Pattern pat = Pattern.compile(reg);

        if(text == null)
            return false;

        return pat.matcher(text).matches();
    }

    public static boolean isValidUsername(String username){
        String reg = "^[a-zA-Z0-9]{3,20}$";
        Pattern pat = Pattern.compile(reg);

        if(username == null)
            return false;

        return pat.matcher(username).matches();
    }

    public static boolean isValidSearchField(String username){
        String reg = "^[a-zA-Z0-9]*$";
        Pattern pat = Pattern.compile(reg);

        return pat.matcher(username).matches();
    }

    public static boolean isValidRealEstateName(String realEstateName){
        String reg = "^[a-zA-Z0-9\\s]*$";
        Pattern pat = Pattern.compile(reg);

        return pat.matcher(realEstateName).matches();
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;

        if(email.length() > 70)
            return false;

        return pat.matcher(email).matches();
    }
}
