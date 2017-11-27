package com.demo.servicedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.util.List;

/**
 * Author: shishaojie
 * Date: 2017/11/27 0027 14:47
 * Description:
 */
public class SearchActivity extends Activity implements DownloadCallbacks {
    private ListView lv;
    private ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lv = (ListView) findViewById(R.id.lv);
        adapter=new ListAdapter(this);
        lv.setAdapter(adapter);
        TDownloadManager.getInstance().registerCallbackListener(this);
        getData();
    }

    private void getData() {
        List<DownloadModel> models = TDownloadManager.getInstance().getAllModels();
        adapter.setData(models);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TDownloadManager.getInstance().unRegisterCallbackListener(this);
    }

    @Override
    public void onComplete(DownloadModel model, String... args) {

    }

    @Override
    public void onFailed(DownloadModel model, Exception e) {

    }

    @Override
    public void onCanceled(DownloadModel model) {

    }

    @Override
    public void onWait(DownloadModel model) {

    }

    @Override
    public void onProgress(DownloadModel model, String progress) {
        adapter.notifyByModel(model);
    }
}
