package com.tony.downloadlib.downloadproxy;

import android.os.Binder;
import android.util.Log;

import com.tony.downloadlib.interfaces.DownloadActions;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;
import com.tony.downloadlib.task.DownloadImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tony on 2017/11/18.
 */

public class ServiceBinder extends Binder implements DownloadActions {
    private ExecutorService pool;
    private Map<String, DownloadImpl> downloadQueue = new HashMap<>();
    private Vector uiListeners = new Vector();


    public ServiceBinder() {
        this.pool = Executors.newFixedThreadPool(3);
    }

    //region DownloadActions

    @Override
    public void startDownload(DownloadModel model) {
        if (downloadQueue.containsKey(model.getUrl())) {
            return;
        }
//        DownloadTask task = new DownloadTask(model, uiListeners);
//        downloadQueue.put(model.getUrl(), task);
//        task.executeOnExecutor(pool);

        DownloadImpl task = new DownloadImpl(model, uiListeners);
        downloadQueue.put(model.getUrl(), task);
        pool.execute(task);

    }

    @Override
    public void pauseDownload(DownloadModel model) {
        Iterator iterator = downloadQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DownloadImpl> entry = (Map.Entry<String, DownloadImpl>) iterator.next();
            if (entry.getKey().equals(model.getUrl())) {
                Log.d("=T=ServiceBinder", "pauseDownload: " + model.getUrl());
                entry.getValue().cancel(true);
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void pauseAll() {
        Log.d("=T=ServiceBinder", "pauseAll: ");
        Iterator iterator = downloadQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DownloadImpl> entry = (Map.Entry<String, DownloadImpl>) iterator.next();
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

    public void removeTask(DownloadModel model) {
        Iterator iterator=downloadQueue.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, DownloadImpl> entry = (Map.Entry<String, DownloadImpl>) iterator.next();
            if (entry.getKey().equals(model.getUrl())) {
                Log.d("=T=ServiceBinder", "removeTask: " + model.getUrl());
                iterator.remove();
                break;
            }
        }
    }

    //endregion

    //region DownloadCallbacks

    public void registerCallbackListener(DownloadCallbacks uiCallback) {
        uiListeners.add(uiCallback);
    }

    public void unRegisterCallbackListener(DownloadCallbacks uiCallback) {
        if (uiListeners.contains(uiCallback)) {
            uiListeners.remove(uiCallback);
        }
    }

    //endregion


}
