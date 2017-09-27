package com.geocentric.foundation.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.geocentric.foundation.common.Common;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * @author shibo
 * @packageName com.geocentric.foundation.utils
 * @description 获取应用基本信息类
 * @date 15/9/24
 */
public class CommUtil {

    /**
     * 获取已完整安装签名信息
     *
     * @return
     */
    public static String getSignInfo(Context context) {

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            return parseSignature(sign.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseSignature(byte[] signature) {

        try {
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            return getMd5UpperCase(signNumber
                    + cert.getSubjectDN().toString());
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMd5UpperCase(String string) {
        byte[] hash;
        if (null == string) {
            return null;
        }
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString().toUpperCase();
    }

    /**
     * 隐藏系统键盘
     */
    public static void hideSyskeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) Common.application
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * 获取versionName
     * @return
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            versionName = Common.application.getPackageManager().getPackageInfo(Common.application.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取versionCode
     * @return
     */
    public static String getAppVersionCode() {
        String versionCode = "";
        try {
            versionCode = Common.application.getPackageManager().getPackageInfo(Common.application.getPackageName(), 0).versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

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
