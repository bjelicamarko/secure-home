package com.asdf.adminback.services;

import com.asdf.adminback.models.IssuerData;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public interface KeyStoreService {

    KeyPair generateKeyPair();

    void loadKeyStore(String fileName, char[] password);

    void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate);

    void saveKeyStore(String fileName, char[] password);

    IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass);

    Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias);

    PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass);

}
