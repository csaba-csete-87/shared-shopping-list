package com.csabacsete.boilerplateproject.utils;

import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {
    }

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
                    + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}"
                    + "("
                    + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"
                    + ")+"
    );

    public static boolean isEmpty(String s) {
        return null == s || s.length() == 0;
    }

    public static boolean hasLength(String s, int minLength) {
        return null != s && s.length() >= minLength;
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
