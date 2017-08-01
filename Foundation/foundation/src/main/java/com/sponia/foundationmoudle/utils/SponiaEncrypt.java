package com.sponia.foundation.utils;

/**
 * @author shibo
 * @packageName com.sponia.foundation.utils
 * @description
 * @date 16/4/27
 */
public class SponiaEncrypt {

//    public static String getClientHash(String url, String time) {
//        return SponiaSHA1.encrypt(url + (Integer.parseInt(StringUtil.substringAfterIndex(time, 8)) + 1024));
//    }

    public static String getClientHash(String url, String timePartOne, String timePartTwo) {
        int i = Integer.valueOf(timePartTwo) + 1024;
        String finalDecimal = String.valueOf(i);
        int length = finalDecimal.length();
        if (length < 6) {
            for (int j = length; j < 6; j++) {
                finalDecimal = "0" + finalDecimal;
            }
        }
        return SponiaSHA1.encrypt(url + timePartOne + finalDecimal);
    }

}
