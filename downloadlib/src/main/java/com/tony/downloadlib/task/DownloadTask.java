package com.tony.downloadlib.task;

import android.os.AsyncTask;
import android.util.Log;

import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by tony on 2017/11/19.
 */
@Deprecated
public class DownloadTask extends AsyncTask {
    private final DownloadModel model;
    private final Vector<DownloadCallbacks> uiListeners;

    public DownloadTask(DownloadModel model, Vector<DownloadCallbacks> uiListeners) {
        this.model = model;
        this.uiListeners = uiListeners;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        int count = 0;
        while (!isCancelled()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("=T=DownloadTask", "run: " + Thread.currentThread().getName() + "====" + count++);
            if (uiListeners != null) {
                Iterator it = uiListeners.iterator();
                while (it.hasNext()) {
                    DownloadCallbacks listener = (DownloadCallbacks) it.next();
                    // FIXME: 2017/11/19 线程切换有问题
                    listener.onComplete(model);
                }
            }
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        Log.d("=T=DownloadTask", "onCancelled: " + model.getUrl());
        super.onCancelled();
    }
}
