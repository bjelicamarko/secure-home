package com.asdf.myhomeback.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isCommon(final String password) throws IOException {
        File file = ResourceUtils.getFile("classpath:common-passwords.csv");
        boolean isCommon = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals(password)) {
                    isCommon = true;
                    break;
                }
            }
        }
        return isCommon;
    }

}
