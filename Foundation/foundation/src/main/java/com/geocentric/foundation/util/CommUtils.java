package com.geocentric.foundation.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.geocentric.foundation.common.BaseApp;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


public class CommUtils {

    /**
     * 当前屏幕宽度
     */
    private static int screenWidth = -1;

    /**
     * 当前屏幕密度
     */
    private static int densityDip = -1;

    /**
     * 当前屏幕高度
     */
    private static int screenHeight = -1;

    /***
     * 屏幕密度
     */
    private static float density;

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        if (screenHeight < 0) {
            initScreenSize();
        }
        return screenHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        if (screenWidth < 0) {
            initScreenSize();
        }
        return screenWidth;
    }

    public static int getDensityDip() {
        if (densityDip < 0) {
            initScreenSize();
        }
        return densityDip;
    }

    public static float getDensity() {
        return density;
    }

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
            return EncryptUtils.getMd5UpperCase(signNumber
                    + cert.getSubjectDN().toString());
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 隐藏系统键盘
     */
    public static void hideSyskeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) BaseApp.getInstance()
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
            versionName = BaseApp.getInstance().getPackageManager().getPackageInfo(BaseApp.getInstance().getPackageName(), 0).versionName;
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
            versionCode = BaseApp.getInstance().getPackageManager().getPackageInfo(BaseApp.getInstance().getPackageName(), 0).versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 初始化屏幕尺寸
     */
    private static void initScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = BaseApp.getInstance().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        densityDip = dm.densityDpi;
        density = dm.density;
    }

    /**
     * 判断是否为Release版本
     *
     * @return
     */
    public static boolean isDebugVersion() {
        return  (0 != (BaseApp.getInstance().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }


}
