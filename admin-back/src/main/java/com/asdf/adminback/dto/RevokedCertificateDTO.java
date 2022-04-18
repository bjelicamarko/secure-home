package com.asdf.adminback.dto;

public class RevokedCertificateDTO {

    private String alias;
    private String reason;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
