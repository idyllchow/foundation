package com.sponia.foundation.common;

import android.app.Application;

/**
 * @packageName com.sponia.foundation.utils
 * @description base application
 * @date 15/9/8
 * @auther shibo
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Common.application = this;
    }
}
