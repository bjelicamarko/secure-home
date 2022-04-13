package com.asdf.adminback.services;

import javax.crypto.SecretKey;

public interface SymmetricKeyToolService {

    SecretKey generateSecretKey(String algorithm, int keySize);

    byte[] encrypt(String plainText, SecretKey secretKey, String algorithm, String regime, String padding);

    byte[] decrypt(byte[] plainText, SecretKey secretKey, String algorithm, String regime, String padding);
}
