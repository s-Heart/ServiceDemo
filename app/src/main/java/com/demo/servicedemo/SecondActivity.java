package com.demo.servicedemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.interfaces.DownloadCallbacks;
import com.tony.downloadlib.model.DownloadModel;


/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/11/19
 * Time: 下午8:18
 * Description:
 * handle download callback
 */
public class SecondActivity extends Activity implements DownloadCallbacks {

    private TextView tvThread1, tvThread2;
    private int count;
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
    public void callback(final DownloadModel model, String... args) {
        if (model.getUrl().contains(".apk")) {
            if (tvThread1 != null) {
                tvThread1.setText(model.getUrl() + "=====" + args[0]);
            }
        } else if (model.getUrl().contains(".exe")) {
            if (tvThread2 != null) {
                tvThread2.setText(model.getUrl() + "=====" + args[0]);
            }
        } else {
            if (tvThread3 != null) {
                tvThread3.setText(model.getUrl() + "=====" + args[0]);
            }
        }
    }

    @Override
    public void callback2(DownloadModel model, Exception e) {

    }
}
