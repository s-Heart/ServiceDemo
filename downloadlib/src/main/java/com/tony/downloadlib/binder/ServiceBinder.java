package com.tony.downloadlib.binder;

import android.os.Binder;
import android.util.Log;

import com.tony.downloadlib.DownloadActions;
import com.tony.downloadlib.model.DownloadModel;
import com.tony.downloadlib.task.DownloadTask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tony on 2017/11/18.
 */

public class ServiceBinder extends Binder implements DownloadActions {
    private ExecutorService pool;
    private Map<DownloadModel, DownloadTask> downloadQueue = new HashMap<>();

    public ServiceBinder() {
        this.pool = Executors.newFixedThreadPool(3);
    }

    //region DownloadActions

    @Override
    public void startDownload(DownloadModel model) {
        if (downloadQueue.containsKey(model)) {
            return;
        }
        DownloadTask task = new DownloadTask(model);
        downloadQueue.put(model, task);
        task.executeOnExecutor(pool);

    }

    @Override
    public void pauseDownload(DownloadModel model) {

    }

    @Override
    public void pauseAll() {
        Log.d("==TServiceBinder", "pauseAll: ");
        Iterator iterator = downloadQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<DownloadModel, DownloadTask> entry = (Map.Entry<DownloadModel, DownloadTask>) iterator.next();
            entry.getValue().cancel(true);
            iterator.remove();
        }
    }

    @Override
    public void startAll() {

    }

    //endregion


}
