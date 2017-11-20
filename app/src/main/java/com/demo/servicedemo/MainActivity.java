package com.demo.servicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener, DownloadCallbacks {
    String file1 = "http://appstore.koolearn.com/files/apps/Koolearn_v3.1.5.apk";
    String file2 = "http://appstore.koolearn.com/files/apps/Koolearn_v2.4.10.exe";
    String file3 = "http://appstore.koolearn.com/files/apps/Koolearn_v0.8.9.dmg";
    DownloadModel model1;
    ProgressBar bar1, bar2, bar3;
    DownloadModel model2;
    DownloadModel model3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_download).setOnClickListener(this);
        findViewById(R.id.btn_stop_download).setOnClickListener(this);
        findViewById(R.id.btn_start_all).setOnClickListener(this);
        findViewById(R.id.btn_pause_all).setOnClickListener(this);

        bar1 = (ProgressBar) findViewById(R.id.progressbar_1);
        bar1.setMax(100);
        bar2 = (ProgressBar) findViewById(R.id.progressbar_2);
        bar2.setMax(100);
        bar3 = (ProgressBar) findViewById(R.id.progressbar_3);
        bar3.setMax(100);

        findViewById(R.id.btn_jump_activity).setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TDownloadManager.getInstance().registerCallbackListener(MainActivity.this);
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TDownloadManager.getInstance().unRegisterCallbackListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_download:
                Toast.makeText(getApplicationContext(), "开始下载", Toast.LENGTH_SHORT).show();
                if (model1 == null) {
                    model1 = new DownloadModel.Builder()
                            .url(file1)
                            .build();
                    TDownloadManager.getInstance().startDownload(model1);
                    return;
                }
                if (model2 == null) {
                    model2 = new DownloadModel.Builder()
                            .url(file2)
                            .build();
                    TDownloadManager.getInstance().startDownload(model2);
                    return;
                }
                if (model3 == null) {
                    model3 = new DownloadModel.Builder()
                            .url(file3)
                            .build();
                    TDownloadManager.getInstance().startDownload(model3);
                    return;
                }
                break;
            case R.id.btn_stop_download:
                Toast.makeText(getApplicationContext(), "停止下载", Toast.LENGTH_SHORT).show();
                if (model1 != null) {
                    TDownloadManager.getInstance().pauseDownload(model1);
                    model1 = null;
                    return;
                }
                if (model2 != null) {
                    TDownloadManager.getInstance().pauseDownload(model2);
                    model2 = null;
                    return;
                }

                if (model3 != null) {
                    TDownloadManager.getInstance().pauseDownload(model3);
                    model3 = null;
                    return;
                }

                break;
            case R.id.btn_start_all:
                Toast.makeText(getApplicationContext(), "开始全部", Toast.LENGTH_SHORT).show();
                List<DownloadModel> models = new ArrayList<>();
                models.add(new DownloadModel.Builder().url(file1).build());
                models.add(new DownloadModel.Builder().url(file2).build());
                models.add(new DownloadModel.Builder().url(file3).build());
                TDownloadManager.getInstance().startAll(models);
                break;
            case R.id.btn_pause_all:
                Toast.makeText(getApplicationContext(), "停止全部", Toast.LENGTH_SHORT).show();
                TDownloadManager.getInstance().pauseAll();
                break;

            case R.id.btn_jump_activity:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }

    @Override
    public void onComplete(DownloadModel model, String... args) {

    }

    @Override
    public void onFailed(DownloadModel model, Exception e) {

    }

    @Override
    public void onCanceled() {

    }

    @Override
    public void onWait(DownloadModel model) {

    }

    @Override
    public void onProgress(DownloadModel model, String progress) {
//        Log.d("=T=MainActivity", "onProgress: " + progress);
        if (model.getUrl().contains(".apk")) {
            bar1.setProgress(Integer.valueOf(progress));
        }
        if (model.getUrl().contains(".exe")) {
            bar2.setProgress(Integer.valueOf(progress));
        }
        if (model.getUrl().contains(".dmg")) {
            bar3.setProgress(Integer.valueOf(progress));
        }
    }
}
