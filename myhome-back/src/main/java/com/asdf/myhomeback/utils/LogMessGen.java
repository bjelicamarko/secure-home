package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.models.AppUser;

public class LogMessGen {

    public static String successfulLogin(String username) {
        return String.format("User with username: '%s' successfully logged in", username);
    }

    public static String badCredentialLogin(String username) {
        return String.format("Login failed with bad credentials for username: '%s'", username);
    }

    public static String userNotFoundLogin(String username) {
        return String.format("Login failed with non-existent username: '%s'", username);
    }

    public static String accountLockedLogin(String username) {
        return String.format("Login failed for locked account for username: '%s'", username);
    }

    public static String accountUnverifiedLogin(String username) {
        return String.format("Login failed for unverified account for username: '%s'", username);
    }

    public static String successfulLogout(String username) {
        return String.format("User with username: '%s' successfully logged out", username);
    }

//    public static String successfulPathOperation(String username, String urlPath) {
//        return String.format("User with username: '%s' successfully finished operation on url path: '%s'", username, urlPath);
//    }

    public static String successfulDeleteUser(String username, Long userId) {
        return String.format("User with username: '%s' successfully deleted user with id: '%s'", username, userId);
    }

    public static String successfulUnlockUser(String username, Long userId) {
        return String.format("User with username: '%s' successfully unlocked user with id: '%s'", username, userId);
    }

    public static String successfulRegistration(String username) {
        return String.format("User with username: '%s' successfully registered", username);
    }

    public static String successfulAccVerification(String username) {
        return String.format("Account with username: '%s' successfully verified", username);
    }

    public static String exMessUser(String username, String exceptionMessage) {
        return String.format("Account with username: '%s' triggered exception with message: '%s'", username, exceptionMessage);
    }

    public static String exMessRegistration(String username, String exceptionMessage) {
        return String.format("While doing registration for username: '%s' exception is triggered with message: '%s'", username, exceptionMessage);
    }

    public static String internalServerError() {
        return "An unknown error happened";
    }

}
