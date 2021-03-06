package com.tony.downloadlib;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.tony.downloadlib.db.DBProxy;
import com.tony.downloadlib.db.TDBManager;
import com.tony.downloadlib.downloadproxy.ServiceBinder;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okio.Okio;

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
            Log.d("=T=DownloadManager", "init: ");
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

    public Context getContext() {
        return context;
    }

    //region download proxy

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof ServiceBinder) {
            //get download proxy
            this.downloadProxy = (ServiceBinder) service;
            Log.d("=T=DownloadManager", "onServiceConnected: " + downloadProxy);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("=T=DownloadManager", "onServiceDisconnected: ");
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

    public void removeTask(DownloadModel model) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.removeTask(model);
    }

    public void deleteDownload(DownloadModel model) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.deleteDownload(model);
    }

    public void deleteDownloads(List<DownloadModel> models) {
        if (this.downloadProxy == null) {
            return;
        }
        downloadProxy.deleteDownloads(models);
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

    //region DB

    public List<DownloadModel> getAllModels() {
        return DBProxy.getAllModels();
    }

    //endregion

    //region Okio

    public void doCopy() {
        try {
            long size = Okio.buffer(Okio.sink(new File("/output.txt"))).writeAll(Okio.source(new File("/input.txt")));
            Log.d("=T=TDownloadManager", "doCopy: " + size);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //endregion


}
