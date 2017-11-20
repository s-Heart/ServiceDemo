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
        Log.d("=T=DownloadService", "onBind: ");
        return binder;
    }

    //endregion


    public TDownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("=T=DownloadService", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("=T=DownloadService", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("=T=DownloadService", "onDestroy: ");
        if (binder != null) {
            binder.pauseAll();
        }
        super.onDestroy();
    }
}
