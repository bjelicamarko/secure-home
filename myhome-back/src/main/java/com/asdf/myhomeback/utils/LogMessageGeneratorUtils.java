package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.models.AppUser;

public class LogMessageGeneratorUtils {

    public static String genSuccessfulLoginMess(String username) {
        return String.format("User with username: '%s' successfully logged in", username);
    }

    public static String genBadCredentialLoginMess(String username) {
        return String.format("Login failed with bad credentials for username: '%s'", username);
    }

    public static String genUserNotFoundLoginMess(String username) {
        return String.format("Login failed with non-existent username: '%s'", username);
    }

    public static String genAccountLockedLoginMess(String username) {
        return String.format("Login failed with locked account for username: '%s'", username);
    }

    public static String genAccountUnverifiedLoginMess(String username) {
        return String.format("Login failed with unverified account for username: '%s'", username);
    }

    public static String genSuccessfulLogoutMess(String username) {
        return String.format("User with username: '%s' successfully logged out", username);
    }

    public static String genSuccessfulPathOperationMess(String username, String urlPath) {
        return String.format("User with username: '%s' successfully finished operation on url path: '%s'", username, urlPath);
    }
}
