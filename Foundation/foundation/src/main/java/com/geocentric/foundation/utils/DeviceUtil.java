package com.geocentric.foundation.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.geocentric.foundation.common.Common;

import java.io.File;


public class DeviceUtil {

    public static final int LIANTONGWAP = 0x0001;// 联通
    public static final int DIANXINWAP = 0x0002;// 电信
    public static final int YIDONGWAP = 0x0003;// 移动
    public static final int WIFI = 0x0004;

    /**
     * 检查网络状态是否可用(非蓝牙)
     *
     * @return true 可用 false不可用
     */
    public static boolean checkNetWorkAvailable() {
        ConnectivityManager mConenctivity = (ConnectivityManager) Common.application
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConenctivity.getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }

    /**
     * 检查网络状态 判断网络WAP代理专用
     *
     * @return -1为网络不可用，其他为联通，移动，电信，WIFI四种状态
     */
    @Deprecated
    public static int checkNetWorkStatus() {
        ConnectivityManager mConenctivity = (ConnectivityManager) Common.application.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConenctivity.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                final String netType = info.getExtraInfo();
                if (netType.contains("wap")) {
                    if (netType.contains("uniwap")) {
                        return LIANTONGWAP;
                    } else if (netType.contains("ctwap")) {
                        return DIANXINWAP;
                    } else {
                        return YIDONGWAP;
                    }
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return WIFI;
            }
        }
        return -1;
    }

    /**
     * 判断网络制式
     *
     * @return 网络制式
     */
    public static String checkNetWorkStatusV1() {
        if (checkNetWorkAvailable()) {
            ConnectivityManager mConenctivity = (ConnectivityManager) Common.application
                    .getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConenctivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // 判断为是手机网络,返回手机网络具体制式
                    final String netType = info.getExtraInfo();
                    return netType;
                } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    // 判断为是WIFI网络,返回WIFI网络制式
                    final String netType = info.getTypeName();
                    return netType;
                }
            }
        }
        return null;
    }


    /**
     * @功能：获取保存路径
     */
    public static String getImagePath(Context context){
        String imageDirString = com.geocentric.foundation.utils.FileUtil.getStorageDirectory() + File.separator + "images";

        File imageDir = new File(imageDirString);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDirString;
    }


    /**
     * @功能：删除图片
     */
    public static boolean delete(String fName) {
        File f = new File(fName);
        if (f.exists()) {
            return f.delete();
        } else {
            return false;
        }
    }

    /**
     * @功能： 判断sdcard的状态
     */
    public static boolean isCardExist() {
        String status = Environment.getExternalStorageState();
        //SD已经挂载,可以使用
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else if (status.equals(Environment.MEDIA_REMOVED)) {
            //SD卡已经已经移除
            com.geocentric.foundation.utils.LogUtil.defaultLog("SD卡已经移除或不存在");
            return false;

        } else if (status.equals(Environment.MEDIA_SHARED)) {
            //SD卡正在使用中
            com.geocentric.foundation.utils.LogUtil.defaultLog("SD卡正在使用中");
            return false;

        } else if (status.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            //SD卡只能读，不能写
            com.geocentric.foundation.utils.LogUtil.defaultLog("SD卡只能读，不能写");
            return false;

        } else {
            //SD卡的其它情况
            com.geocentric.foundation.utils.LogUtil.defaultLog("SD卡已经移除或不存在");
            return false;
        }
    }

    /**
     * 获取SD卡路径
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        if (isCardExist()) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.toString();
        }
        return null;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 系统版本
     *
     * @return
     */
    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取主板信息
     *
     * @return
     */
    public static String getBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * android系统定制商
     *
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * cpu指令集
     *
     * @return
     */
    public static String getCPUABI() {
        return android.os.Build.CPU_ABI;
    }

    /**
     * 设备参数
     *
     * @return
     */
    public static String getDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * 显示屏参数
     *
     * @return
     */
    public static String getDisplay() {
        return android.os.Build.DISPLAY;
    }

    /**
     * 硬件名称
     *
     * @return
     */
    public static String getFingerprint() {
        return android.os.Build.FINGERPRINT;
    }

    /**
     * 获取渠道名
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName() {
        String channel = "";
        try {
            ApplicationInfo appInfo = Common.application.getPackageManager().getApplicationInfo(Common.application.getPackageName(), PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return channel;
    }
}
