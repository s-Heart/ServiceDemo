package com.demo.servicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    DownloadModel model1 = new DownloadModel.Builder().url(file1).build();
    DownloadModel model2 = new DownloadModel.Builder().url(file2).build();
    DownloadModel model3 = new DownloadModel.Builder().url(file3).build();
    ProgressBar bar1, bar2, bar3;
    private TextView mState1, mState2, mState3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_download_1).setOnClickListener(this);
        findViewById(R.id.btn_stop_download_1).setOnClickListener(this);
        findViewById(R.id.btn_delete_download_1).setOnClickListener(this);
        mState1 = (TextView) findViewById(R.id.tv_thread1_state);

        findViewById(R.id.btn_start_download_2).setOnClickListener(this);
        findViewById(R.id.btn_stop_download_2).setOnClickListener(this);
        findViewById(R.id.btn_delete_download_2).setOnClickListener(this);

        mState2 = (TextView) findViewById(R.id.tv_thread2_state);

        findViewById(R.id.btn_start_download_3).setOnClickListener(this);
        findViewById(R.id.btn_stop_download_3).setOnClickListener(this);
        findViewById(R.id.btn_delete_download_3).setOnClickListener(this);

        mState3 = (TextView) findViewById(R.id.tv_thread3_state);


        findViewById(R.id.btn_start_all).setOnClickListener(this);
        findViewById(R.id.btn_pause_all).setOnClickListener(this);
        findViewById(R.id.btn_delete_all).setOnClickListener(this);


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
            case R.id.btn_start_download_1:
                model1 = new DownloadModel.Builder().url(file1).build();
                TDownloadManager.getInstance().startDownload(model1);
                break;
            case R.id.btn_stop_download_1:
                TDownloadManager.getInstance().pauseDownload(model1);
                break;
            case R.id.btn_delete_download_1:
                TDownloadManager.getInstance().deleteDownload(model1);
                break;
            case R.id.btn_start_download_2:
                model2 = new DownloadModel.Builder().url(file1).build();
                TDownloadManager.getInstance().startDownload(model2);
                break;
            case R.id.btn_stop_download_2:
                TDownloadManager.getInstance().pauseDownload(model2);
                break;
            case R.id.btn_delete_download_2:
                TDownloadManager.getInstance().deleteDownload(model2);
                break;
            case R.id.btn_start_download_3:
                model3 = new DownloadModel.Builder().url(file1).build();
                TDownloadManager.getInstance().startDownload(model3);
                break;
            case R.id.btn_stop_download_3:
                TDownloadManager.getInstance().pauseDownload(model3);
                break;
            case R.id.btn_delete_download_3:
                TDownloadManager.getInstance().deleteDownload(model3);
                break;
            case R.id.btn_start_all:
                Toast.makeText(getApplicationContext(), "开始全部", Toast.LENGTH_SHORT).show();
                List<DownloadModel> models = new ArrayList<>();
                models.add(model1);
                models.add(model2);
                models.add(model3);
                TDownloadManager.getInstance().startAll(models);
                break;
            case R.id.btn_pause_all:
                Toast.makeText(getApplicationContext(), "停止全部", Toast.LENGTH_SHORT).show();
                TDownloadManager.getInstance().pauseAll();
                break;
            case R.id.btn_delete_all:
                List<DownloadModel> models2 = new ArrayList<>();
                models2.add(model1);
                models2.add(model2);
                models2.add(model3);
                TDownloadManager.getInstance().deleteDownloads(models2);
                break;
            case R.id.btn_jump_activity:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }

    @Override
    public void onComplete(DownloadModel model, String... args) {
        if (model.getUrl().contains(".apk")) {
            mState1.setText("state:" + "onComplete...");
            bar1.setProgress(100);
        }
        if (model.getUrl().contains(".exe")) {
            mState2.setText("state:" + "onComplete...");
            bar2.setProgress(100);
        }
        if (model.getUrl().contains(".dmg")) {
            mState3.setText("state:" + "onComplete...");
            bar3.setProgress(100);
        }
    }

    @Override
    public void onFailed(DownloadModel model, Exception e) {
        if (model.getUrl().contains(".apk")) {
            mState1.setText("state:" + "onFailed...");
        }
        if (model.getUrl().contains(".exe")) {
            mState2.setText("state:" + "onFailed...");
        }
        if (model.getUrl().contains(".dmg")) {
            mState3.setText("state:" + "onFailed...");
        }
    }

    @Override
    public void onCanceled(DownloadModel model) {
        if (model.getUrl().contains(".apk")) {
            mState1.setText("state:" + "onCanceled...");
        }
        if (model.getUrl().contains(".exe")) {
            mState2.setText("state:" + "onCanceled...");
        }
        if (model.getUrl().contains(".dmg")) {
            mState3.setText("state:" + "onCanceled...");
        }
    }

    @Override
    public void onWait(DownloadModel model) {
        if (model.getUrl().contains(".apk")) {
            mState1.setText("state:" + "onWait...");
        }
        if (model.getUrl().contains(".exe")) {
            mState2.setText("state:" + "onWait...");
        }
        if (model.getUrl().contains(".dmg")) {
            mState3.setText("state:" + "onWait...");
        }
    }

    @Override
    public void onProgress(DownloadModel model, String progress) {
//        Log.d("=T=MainActivity", "onProgress: " + progress);
        if (model.getUrl().contains(".apk")) {
            bar1.setProgress(Integer.valueOf(progress));
            mState1.setText("state:" + "onProgress...");
        }
        if (model.getUrl().contains(".exe")) {
            bar2.setProgress(Integer.valueOf(progress));
            mState2.setText("state:" + "onProgress...");
        }
        if (model.getUrl().contains(".dmg")) {
            bar3.setProgress(Integer.valueOf(progress));
            mState3.setText("state:" + "onProgress...");
        }
    }
}
