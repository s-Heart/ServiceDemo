package com.tony.downloadlib;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.tony.downloadlib.db.TDBManager;
import com.tony.downloadlib.downloadproxy.ServiceBinder;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.util.List;

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
            initBinder(context);
            initDB(context);
        }
    }

    private void initDB(Context context) {
        TDBManager.getInstance().init(context);
    }

    private void initBinder(Context context) {
        context.startService(new Intent(context, TDownloadService.class));
        context.bindService(new Intent(context, TDownloadService.class), this, Context.BIND_AUTO_CREATE);
    }

    //region download proxy

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

    public void pauseDownload(DownloadModel downloadModel) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.pauseDownload(downloadModel);
    }

    public void startAll(List<DownloadModel> models) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.startAll(models);
    }

    //endregion

    //region uiCallbacks

    public void registerCallbackListener(DownloadCallbacks uiCallback) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.registerCallbackListener(uiCallback);
    }

    public void unRegisterCallbackListener(DownloadCallbacks uiCallback) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.unRegisterCallbackListener(uiCallback);
    }

    //endregion


}
