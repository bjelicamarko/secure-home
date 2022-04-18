package com.asdf.adminback.exceptions;

public class CertificateNotFound extends Exception {
    public CertificateNotFound(String message) { super(String.format("Certificate (alias: %s) not found!", message)); }
}
