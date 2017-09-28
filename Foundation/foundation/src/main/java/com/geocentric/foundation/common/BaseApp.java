package com.geocentric.foundation.common;

import android.app.Application;

public class BaseApp extends Application {

    public static Application baseApp;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
    }
}
