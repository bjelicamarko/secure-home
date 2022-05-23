package com.asdf.myhomeback.dto;

public class UserTokenStateDTO {
	
    private String accessToken;
    private Long expiresIn;
    private String exception;

    public UserTokenStateDTO() {
        this.accessToken = null;
        this.expiresIn = null;
        this.exception = null;
    }

    public UserTokenStateDTO(String accessToken, long expiresIn) {
        this();
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public UserTokenStateDTO(String exception) {
        this();
        this.exception = exception;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getException() { return exception;}

    public void setException(String exception) { this.exception = exception;
    }
}