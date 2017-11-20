package com.tony.downloadlib.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

/**
 * Author: shishaojie
 * Date: 2017/11/20 0020 10:17
 * Description:
 * 控制线程切换
 */
public abstract class BaseTask implements Runnable {

    private Handler innerHandler = new InnerHandler(Looper.getMainLooper());

    void notifyUIFromWorkThread(int callbackType, DownloadCallbacks listener, DownloadModel model, Exception e, String... args) {
        // 线程切换
        Log.d("=T=BaseTask", "notifyUIFromWorkThread: " + Thread.currentThread().getName());
        Message msg = innerHandler.obtainMessage();
        msg.what = callbackType;
        Bundle bundle = new Bundle();
        bundle.putSerializable("listener", listener);
        bundle.putSerializable("model", model);
        bundle.putSerializable("exception", e);
        bundle.putStringArray("args", args);
        msg.setData(bundle);
        msg.sendToTarget();
    }

    private class InnerHandler extends Handler {
        public InnerHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("=T=InnerHandler", "handleMessage: " + Thread.currentThread().getName());
            int methodCode = msg.what;
            Bundle data = msg.getData();
            DownloadCallbacks listener = (DownloadCallbacks) data.getSerializable("listener");
            DownloadModel model = (DownloadModel) data.getSerializable("model");
            Exception e = (Exception) data.getSerializable("exception");
            String[] args = data.getStringArray("args");
            switch (methodCode) {
                case DownloadCallbacks.CallbackType.METHOD_CALLBACK:
                    listener.callback(model, args);
                    break;
                case DownloadCallbacks.CallbackType.METHOD_CALLBACK2:
                    listener.callback2(model, e);
                    break;
            }
        }
    }
}
