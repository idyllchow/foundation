package com.geocentric.foundation.utils;

import android.widget.Toast;

import com.geocentric.foundation.common.BaseApp;

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
