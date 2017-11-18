package com.tony.downloadlib.binder;

import android.os.Binder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.tony.downloadlib.DownloadActions;
import com.tony.downloadlib.model.DownloadModel;

/**
 * Created by tony on 2017/11/18.
 */

public class ServiceBinder extends Binder implements DownloadActions {
    private Executor pool;

    public ServiceBinder() {
        this.pool = Executors.newFixedThreadPool(3);
    }

    @Override
    public void startDownload(DownloadModel model) {

    }

    @Override
    public void pauseDownload(DownloadModel model) {

    }

    @Override
    public void pauseAll() {

    }

    @Override
    public void startAll() {

    }
}
