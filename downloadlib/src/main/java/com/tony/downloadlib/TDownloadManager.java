package com.tony.downloadlib;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.tony.downloadlib.binder.ServiceBinder;
import com.tony.downloadlib.model.DownloadModel;

/**
 * Created by tony on 2017/11/18.
 */

public class TDownloadManager implements ServiceConnection {
    private Context context;

    private static TDownloadManager sInstance;
    private ServiceBinder downloadProxy;

    private TDownloadManager() {
    }

    public static TDownloadManager getInstance() {
        if (sInstance == null) {
            synchronized (TDownloadManager.class) {
                if (sInstance == null) {
                    sInstance = new TDownloadManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        if (this.context == null) {
            this.context = context;
            Log.d("==TDownloadManager", "init: ");
//            context.startService(new Intent(context, TDownloadService.class));
            context.bindService(new Intent(context, TDownloadService.class), this, Context.BIND_AUTO_CREATE);
        }
    }

    //region get download proxy

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof ServiceBinder) {
            //get download proxy
            this.downloadProxy = (ServiceBinder) service;
            Log.d("==TDownloadManager", "onServiceConnected: " + downloadProxy);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("==TDownloadManager", "onServiceDisconnected: ");
    }

    public void startDownload(DownloadModel downloadModel) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.startDownload(downloadModel);
    }

    public void pauseAll() {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.pauseAll();
    }

    //endregion


}
