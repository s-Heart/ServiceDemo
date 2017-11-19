package com.tony.downloadlib.task;

import android.os.AsyncTask;
import android.util.Log;

import com.tony.downloadlib.model.DownloadModel;

/**
 * Created by tony on 2017/11/19.
 */

public class DownloadTask extends AsyncTask {
    private final DownloadModel model;

    public DownloadTask(DownloadModel model) {
        this.model = model;
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
            Log.d("==TDownloadTask", "run: " + Thread.currentThread().getName() + "====" + count++);
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        Log.d("==TDownloadTask", "onCancelled: " + model.getUrl());
        super.onCancelled();
    }
}
