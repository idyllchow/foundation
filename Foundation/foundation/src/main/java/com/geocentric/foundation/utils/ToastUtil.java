package com.geocentric.foundation.utils;

import android.widget.Toast;

import com.geocentric.foundation.common.BaseApp;

/**
 * @packageName com.geocentric.foundation.utils
 * @description toast管理类
 * @date 15/9/10
 * @auther shibo
 */
public class ToastUtil {

    /**
     * 显示toast
     *
     * @param showStr
     */
    public static void showShortToast(final String showStr) {
        Toast.makeText(BaseApp.baseApp, showStr, Toast.LENGTH_SHORT).show();
    }
}
