package com.asdf.myhomeback.services.impls;

import com.asdf.myhomeback.services.SymmetricKeyToolService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Service
public class SymmetricKeyToolServiceImpl implements SymmetricKeyToolService {

    public static final String skStr = "R7x1Gg5brIiKXpFQAy20Hw==";

    @PostConstruct
    private void postConstruct() {
        // SecretKey secretKey = generateSecretKey("AES", 128);
        // System.out.println(new String(secretKey.getEncoded(), StandardCharsets.UTF_8));
    }

    @Override
    public String decryptMessage(String ciphertext) throws Exception {

        byte[] cipherbytes = Base64.getDecoder().decode(ciphertext);

        byte[] initVector = Arrays.copyOfRange(cipherbytes,0,16);

        byte[] messagebytes = Arrays.copyOfRange(cipherbytes,16,cipherbytes.length);

        IvParameterSpec iv = new IvParameterSpec(initVector);
        SecretKeySpec skeySpec = new SecretKeySpec(skStr.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        // Convert the ciphertext Base64-encoded String back to bytes, and
        // then decrypt
        byte[] byte_array = cipher.doFinal(messagebytes);

        // Return plaintext as String
        return new String(byte_array, StandardCharsets.UTF_8);
    }

}
