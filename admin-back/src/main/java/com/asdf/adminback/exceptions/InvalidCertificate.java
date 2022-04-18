package com.asdf.adminback.exceptions;

public class InvalidCertificate extends Exception{
    public InvalidCertificate(String message) { super(String.format("Invalid certificate (alias: %s)!", message)); }
}
