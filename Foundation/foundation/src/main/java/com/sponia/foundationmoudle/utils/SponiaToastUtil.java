package com.sponia.foundation.utils;

import android.widget.Toast;

import com.sponia.foundation.common.Common;

/**
 * @packageName com.sponia.foundation.utils
 * @description toast管理类
 * @date 15/9/10
 * @auther shibo
 */
public class SponiaToastUtil {

    /**
     * 显示toast
     *
     * @param showStr
     */
    public static void showShortToast(final String showStr) {
        Toast.makeText(Common.application, showStr, Toast.LENGTH_SHORT).show();
    }
}
