package com.asdf.adminback.services;

import com.asdf.adminback.models.IssuerData;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public interface KeyStoreService {

    KeyPair generateKeyPair();

    void loadKeyStore(String fileName, char[] password);

    void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate);

    void saveKeyStore(String fileName, char[] password);

    IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass);

    Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias);

    PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass);

    Long generateNextSerialNumber();
}
