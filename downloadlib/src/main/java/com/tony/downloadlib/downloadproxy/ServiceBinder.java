package com.tony.downloadlib.downloadproxy;

import android.os.Binder;
import android.util.Log;

import com.tony.downloadlib.TDBManager;
import com.tony.downloadlib.interfaces.DownloadActions;
import com.tony.downloadlib.model.DownloadModel;
import com.tony.downloadlib.task.DownloadTask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tony on 2017/11/18.
 */

public class ServiceBinder extends Binder implements DownloadActions {
    private ExecutorService pool;
    private Map<String, DownloadTask> downloadQueue = new HashMap<>();


    public ServiceBinder() {
        Log.d("==TServiceBinder", "ServiceBinder: ");
        this.pool = Executors.newFixedThreadPool(3);
    }

    //region DownloadActions

    @Override
    public void startDownload(DownloadModel model) {
        if (downloadQueue.containsKey(model.getUrl())) {
            return;
        }
        DownloadTask task = new DownloadTask(model);
        downloadQueue.put(model.getUrl(), task);
        task.executeOnExecutor(pool);

        TDBManager.getInstance().getDaoSession().getDownloadModelDao().insertOrReplace(model);

    }

    @Override
    public void pauseDownload(DownloadModel model) {
        Log.d("==TServiceBinder", "pauseDownload: " + model.getUrl());
        Iterator iterator = downloadQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DownloadTask> entry = (Map.Entry<String, DownloadTask>) iterator.next();
            if (entry.getKey().equals(model.getUrl())) {
                entry.getValue().cancel(true);
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void pauseAll() {
        Log.d("==TServiceBinder", "pauseAll: ");
        Iterator iterator = downloadQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DownloadTask> entry = (Map.Entry<String, DownloadTask>) iterator.next();
            entry.getValue().cancel(true);
            iterator.remove();
        }
    }

    @Override
    public void startAll(List<DownloadModel> models) {
        Iterator iterator = models.iterator();
        while (iterator.hasNext()) {
            DownloadModel model = (DownloadModel) iterator.next();
            if (downloadQueue.containsKey(model.getUrl())) {
                continue;
            }
            startDownload(model);
        }
    }

    //endregion


}
