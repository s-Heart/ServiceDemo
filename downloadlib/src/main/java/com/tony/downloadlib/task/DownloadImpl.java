package com.tony.downloadlib.task;

import android.util.Log;

import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.util.Iterator;
import java.util.Vector;

/**
 * Author: shishaojie
 * Date: 2017/11/20 0020 10:17
 * Description:
 */
public class DownloadImpl extends BaseTask {

    private final DownloadModel model;
    private final Vector<DownloadCallbacks> uiListeners;
    private boolean mIsCancelled;

    public DownloadImpl(DownloadModel model, Vector<DownloadCallbacks> uiListeners) {
        this.model = model;
        this.uiListeners = uiListeners;
    }

    @Override
    public void run() {
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
                    notifyUIFromWorkThread(DownloadCallbacks.CallbackType.METHOD_CALLBACK, listener, model, null, count + "");
                }
            }
        }
    }

    private boolean isCancelled() {
        return mIsCancelled;
    }

    public void cancel(boolean isCancelled) {
        this.mIsCancelled = isCancelled;
    }
}
