package com.geocentric.foundation.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author shibo
 * @packageName com.geocentric.foundation.utils
 * @description
 * @date 16/4/27
 */
public class SHA1 {

    public static String encrypt(String strSrc) {
        final String ENC_NAME = "SHA-1";
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(ENC_NAME);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
