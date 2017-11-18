package com.tony.downloadlib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tony.downloadlib.binder.ServiceBinder;

public class LocalService extends Service {
    //region binder
    private ServiceBinder binder = new ServiceBinder();


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("=====", "onBind: ");
        return binder;
    }
    //endregion


    public LocalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("=====", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("=====", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("=====", "onDestroy: ");
        if (binder != null) {
            binder.pauseAll();
        }
        super.onDestroy();
    }
}
