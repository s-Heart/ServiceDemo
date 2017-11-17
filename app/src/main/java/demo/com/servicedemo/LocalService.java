package demo.com.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
    private Binder binder = new ServiceBinder();

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
    public IBinder onBind(Intent intent) {
        Log.d("=====", "onBind: ");
        return binder;
    }

    @Override
    public void onDestroy() {
        Log.d("=====", "onDestroy: ");
        super.onDestroy();
    }

    private class ServiceBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }
}
