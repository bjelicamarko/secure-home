package com.asdf.adminback.dto;

public class ExtendedKeyUsageDTO {

    private boolean adobePdfSigning;
    private boolean documentSigning;
    private boolean emailProtection;
    private boolean ipSecurityEndSystem;
    private boolean ocspSigning;
    private boolean timeStamping;
    private boolean tlsWebClientAuthentication;
    private boolean tlsWebServerAuthentication;
    private boolean tslSigning;

    public ExtendedKeyUsageDTO() { }

    public boolean isAdobePdfSigning() {
        return adobePdfSigning;
    }

    public void setAdobePdfSigning(boolean adobePdfSigning) {
        this.adobePdfSigning = adobePdfSigning;
    }

    public boolean isDocumentSigning() {
        return documentSigning;
    }

    public void setDocumentSigning(boolean documentSigning) {
        this.documentSigning = documentSigning;
    }

    public boolean isEmailProtection() {
        return emailProtection;
    }

    public void setEmailProtection(boolean emailProtection) {
        this.emailProtection = emailProtection;
    }

    public boolean isIpSecurityEndSystem() {
        return ipSecurityEndSystem;
    }

    public void setIpSecurityEndSystem(boolean ipSecurityEndSystem) {
        this.ipSecurityEndSystem = ipSecurityEndSystem;
    }

    public boolean isOcspSigning() {
        return ocspSigning;
    }

    public void setOcspSigning(boolean ocspSigning) {
        this.ocspSigning = ocspSigning;
    }

    public boolean isTimeStamping() {
        return timeStamping;
    }

    public void setTimeStamping(boolean timeStamping) {
        this.timeStamping = timeStamping;
    }

    public boolean isTlsWebClientAuthentication() {
        return tlsWebClientAuthentication;
    }

    public void setTlsWebClientAuthentication(boolean tlsWebClientAuthentication) {
        this.tlsWebClientAuthentication = tlsWebClientAuthentication;
    }

    public boolean isTlsWebServerAuthentication() {
        return tlsWebServerAuthentication;
    }

    public void setTlsWebServerAuthentication(boolean tlsWebServerAuthentication) {
        this.tlsWebServerAuthentication = tlsWebServerAuthentication;
    }

    public boolean isTslSigning() {
        return tslSigning;
    }

    public void setTslSigning(boolean tslSigning) {
        this.tslSigning = tslSigning;
    }
}
