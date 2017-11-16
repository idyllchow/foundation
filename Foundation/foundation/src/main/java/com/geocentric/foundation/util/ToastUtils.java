package com.geocentric.foundation.util;

import android.widget.Toast;

import com.geocentric.foundation.common.BaseApp;

public class ToastUtils {

    /**
     * 显示toast
     *
     * @param showStr
     */
    public static void showShortToast(final String showStr) {
        Toast.makeText(BaseApp.getInstance(), showStr, Toast.LENGTH_SHORT).show();
    }
}
