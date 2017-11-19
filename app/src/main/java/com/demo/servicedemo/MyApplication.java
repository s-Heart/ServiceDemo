package com.demo.servicedemo;

import android.app.Application;

import com.tony.downloadlib.TDownloadManager;

/**
 * Created by tony on 2017/11/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TDownloadManager.getInstance().init(this);
    }

}
