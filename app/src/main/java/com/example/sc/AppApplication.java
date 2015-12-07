package com.example.sc;

import android.app.Application;

/**
 * Created by sc on 15-10-12.
 */
public class AppApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        application=this;
        super.onCreate();
    }
}
