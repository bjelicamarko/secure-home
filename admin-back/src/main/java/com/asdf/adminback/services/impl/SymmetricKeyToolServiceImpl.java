package com.asdf.adminback.services.impl;

import com.asdf.adminback.services.SymmetricKeyToolService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

@Service
public class SymmetricKeyToolServiceImpl implements SymmetricKeyToolService {

    private IvParameterSpec iv;

    @PostConstruct
    private void postConstruct(){
        byte[] ivByte = new byte[16];
        new SecureRandom().nextBytes(ivByte);
        this.iv = new IvParameterSpec(ivByte);
    }

    @Override
    public SecretKey generateSecretKey(String algorithm, int keySize) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(keySize);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] encrypt(String plainText, SecretKey key, String algorithm, String regime, String padding) {
        algorithm = (algorithm == null) ? "AES" : algorithm;
        regime = (regime == null) ? "CBC" : regime;
        padding = (padding == null) ? "PKCS5Padding" : padding;

        try {
            Cipher aesCipherEnc = Cipher.getInstance(String.format("%s/%s/%s", algorithm, regime, padding));

            aesCipherEnc.init(Cipher.ENCRYPT_MODE, key, iv);

            return aesCipherEnc.doFinal(plainText.getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalStateException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] decrypt(byte[] cipherText, SecretKey key, String algorithm, String regime, String padding) {
        algorithm = (algorithm == null) ? "AES" : algorithm;
        regime = (regime == null) ? "CBC" : regime;
        padding = (padding == null) ? "PKCS5Padding" : padding;

        try {
            Cipher aesCipherDec = Cipher.getInstance(String.format("%s/%s/%s", algorithm, regime, padding));

            aesCipherDec.init(Cipher.DECRYPT_MODE, key, iv);

            return aesCipherDec.doFinal(cipherText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalStateException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
