package com.geocentric.foundation.utils;

/**
 * @author shibo
 * @packageName com.geocentric.foundation.utils
 * @description
 * @date 16/4/27
 */
public class Encrypt {

    public static String getClientHash(String url, String timePartOne, String timePartTwo) {
        int i = Integer.valueOf(timePartTwo) + 1024;
        String finalDecimal = String.valueOf(i);
        int length = finalDecimal.length();
        if (length < 6) {
            for (int j = length; j < 6; j++) {
                finalDecimal = "0" + finalDecimal;
            }
        }
        return SHA1.encrypt(url + timePartOne + finalDecimal);
    }

}
