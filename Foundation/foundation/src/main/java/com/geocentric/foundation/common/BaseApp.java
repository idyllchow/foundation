package com.geocentric.foundation.common;

import android.app.Application;

public class BaseApp extends Application {

    private static BaseApp baseApp;

    public static BaseApp getInstance() {
        return baseApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
    }
}
