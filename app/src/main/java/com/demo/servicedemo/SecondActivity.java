package com.demo.servicedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;


/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/11/19
 * Time: 下午8:18
 * Description:
 * handle download onComplete
 */
public class SecondActivity extends Activity implements DownloadCallbacks {

    private TextView tvThread1, tvThread2;
    private TextView tvThread3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvThread1 = (TextView) findViewById(R.id.tv_thread1);
        tvThread2 = (TextView) findViewById(R.id.tv_thread2);
        tvThread3 = (TextView) findViewById(R.id.tv_thread3);
        TDownloadManager.getInstance().registerCallbackListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TDownloadManager.getInstance().unRegisterCallbackListener(this);
    }

    @Override
    public void onComplete(final DownloadModel model, String... args) {
        if (model.getUrl().contains(".apk")) {
            if (tvThread1 != null) {
                tvThread1.setText("已下载完成,请到" + model.getDownloadPath() + "查看");
            }
        } else if (model.getUrl().contains(".exe")) {
            if (tvThread2 != null) {
                tvThread2.setText("已下载完成,请到" + model.getDownloadPath() + "查看");
            }
        } else {
            if (tvThread3 != null) {
                tvThread3.setText("已下载完成,请到" + model.getDownloadPath() + "查看");

            }
        }
    }

    @Override
    public void onFailed(DownloadModel model, Exception e) {
        Log.d("=T=SecondActivity", "onFailed: " + model.getUrl());
        Log.d("=T=SecondActivity", "onFailed: " + e.getMessage());
    }

    @Override
    public void onCanceled() {
        tvThread1.setText("已取消");
    }

    @Override
    public void onWait(DownloadModel model) {
        if (model.getUrl().contains(".apk")) {
            tvThread1.setText("等待中...");
        }
        if (model.getUrl().contains(".exe")) {
            tvThread2.setText("等待中...");
        }
        if (model.getUrl().contains(".dmg")) {
            tvThread3.setText("等待中...");
        }
    }

    @Override
    public void onProgress(DownloadModel model, String progress) {
        if (model.getUrl().contains(".apk")) {
            tvThread1.setText(model.getDownloadPath() + "===>" + progress + "%");
        }
        if (model.getUrl().contains(".exe")) {
            tvThread2.setText(model.getDownloadPath() + "===>" + progress + "%");
        }
        if (model.getUrl().contains(".dmg")) {
            tvThread3.setText(model.getDownloadPath() + "===>" + progress + "%");
        }
    }
}
