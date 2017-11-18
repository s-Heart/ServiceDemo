package com.tony.downloadlib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tony.downloadlib.downloadproxy.ServiceBinder;

public class TDownloadService extends Service {

    //region binder

    private ServiceBinder binder = new ServiceBinder();


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("==TDownloadService", "onBind: ");
        return binder;
    }

    //endregion


    public TDownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("==TDownloadService", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("==TDownloadService", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("==TDownloadService", "onDestroy: ");
        if (binder != null) {
            binder.pauseAll();
        }
        super.onDestroy();
    }
}
