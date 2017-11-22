package com.tony.downloadlib.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.db.TDBManager;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.Iterator;
import java.util.Vector;

/**
 * Author: shishaojie
 * Date: 2017/11/20 0020 10:17
 * Description:
 * 控制线程切换
 */
public abstract class BaseTask implements Runnable {

    private Handler innerHandler = new InnerHandler(Looper.getMainLooper());

    /**
     * @param callbackType {@link DownloadCallbacks.CallbackType}
     * @param listeners
     * @param model
     * @param e
     * @param args
     */
    void notifyUIFromWorkThread(int callbackType, Vector listeners, @NotNull DownloadModel model, Exception e, String args) {
        // 线程切换
//        Log.d("=T=BaseTask", "notifyUIFromWorkThread: " + Thread.currentThread().getName());
        Message msg = innerHandler.obtainMessage();
        msg.what = callbackType;
        Bundle bundle = new Bundle();
        bundle.putSerializable("listeners", listeners);
        bundle.putSerializable("model", model);
        bundle.putSerializable("exception", e);
        bundle.putString("args", args);
        msg.setData(bundle);
        msg.sendToTarget();
    }

    private class InnerHandler extends Handler {
        public InnerHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
//            Log.d("=T=InnerHandler", "handleMessage: " + Thread.currentThread().getName());
            int methodCode = msg.what;
            Bundle data = msg.getData();
            Vector listeners = (Vector) data.getSerializable("listeners");
            DownloadModel model = (DownloadModel) data.getSerializable("model");
            Exception e = (Exception) data.getSerializable("exception");
            String args = data.getString("args");
            Iterator iterator = listeners.iterator();
            switch (methodCode) {
                case DownloadCallbacks.CallbackType.METHOD_ON_COMPLETE:
                    while (iterator.hasNext()) {
                        DownloadCallbacks listener = (DownloadCallbacks) iterator.next();
                        listener.onComplete(model, args);
                    }
                    removeTask(model);
                    break;
                case DownloadCallbacks.CallbackType.METHOD_ON_FAILED:
                    iterator = listeners.iterator();
                    while (iterator.hasNext()) {
                        DownloadCallbacks listener = (DownloadCallbacks) iterator.next();
                        listener.onFailed(model, e);
                    }
                    removeTask(model);
                    break;
                case DownloadCallbacks.CallbackType.METHOD_ON_CANCELED:
                    iterator = listeners.iterator();
                    while (iterator.hasNext()) {
                        DownloadCallbacks listener = (DownloadCallbacks) iterator.next();
                        listener.onCanceled(model);
                    }
                    break;
                case DownloadCallbacks.CallbackType.METHOD_ON_WAIT:
                    iterator = listeners.iterator();
                    while (iterator.hasNext()) {
                        DownloadCallbacks listener = (DownloadCallbacks) iterator.next();
                        listener.onWait(model);
                    }
                    break;
                case DownloadCallbacks.CallbackType.METHOD_ON_PROGRESS:
                    iterator = listeners.iterator();
                    while (iterator.hasNext()) {
                        DownloadCallbacks listener = (DownloadCallbacks) iterator.next();
                        listener.onProgress(model, args);
                    }
                    break;

            }
        }
    }

    /**
     * 已经完成的任务需要将池中任务移除
     *
     * @param model
     */
    private void removeTask(DownloadModel model) {
        TDownloadManager.getInstance().pauseDownload(model);
    }
}
