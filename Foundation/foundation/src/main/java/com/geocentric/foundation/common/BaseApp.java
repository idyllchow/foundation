package com.geocentric.foundation.common;

import android.app.Application;

/**
 * @packageName com.geocentric.foundation.utils
 * @description base application
 * @date 15/9/8
 * @auther shibo
 */
public class BaseApp extends Application {

    public static Application baseApp;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
    }
}
