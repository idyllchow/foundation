package com.sponia.foundation.share;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author shibo
 * @packageName com.sponia.foundation.share
 * @description
 * @date 16/9/28
 */

public class ShareMedia {
    public static final String WEI_XIN = "WeiXin";
    public static final String WEI_XIN_PENG_YOU_QUAN = "WeiXinPengYouQuan";
    public static final String SAVE_LOCAL = "SaveLocal";
    public static final String QQ_ZONE = "QQZone";
    public static final String WEI_BO = "WeiBo";

    @StringDef({WEI_XIN, WEI_XIN_PENG_YOU_QUAN, SAVE_LOCAL, QQ_ZONE, WEI_BO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SharePlatform {}
}
