package com.asdf.myhomeback.Exception;

public class TokenBlacklistedException extends Exception{

    public static final String message = "Token is blacklisted!";

    public TokenBlacklistedException() {
        super(message);
    }
}
