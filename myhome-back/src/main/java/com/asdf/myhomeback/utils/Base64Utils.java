package com.asdf.myhomeback.utils;

import java.io.IOException;

public class Base64Utils {

    public static String encode(byte[] data) {
        return java.util.Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String base64Data) throws IOException {
        return java.util.Base64.getDecoder().decode(base64Data);
    }

}
