package demo.com.servicedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tony.downloadlib.TDownloadManager;
import com.tony.downloadlib.model.DownloadModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_download).setOnClickListener(this);
        findViewById(R.id.btn_stop_download).setOnClickListener(this);
        findViewById(R.id.btn_start_all).setOnClickListener(this);
        findViewById(R.id.btn_pause_all).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_download:
                Toast.makeText(getApplicationContext(), "开始下载", Toast.LENGTH_SHORT).show();
                TDownloadManager.getInstance().startDownload(new DownloadModel());
                break;
            case R.id.btn_stop_download:
                Toast.makeText(getApplicationContext(), "停止下载", Toast.LENGTH_SHORT).show();
                TDownloadManager.getInstance().pauseDownload(new DownloadModel());
                break;
            case R.id.btn_start_all:
                Toast.makeText(getApplicationContext(), "开始全部", Toast.LENGTH_SHORT).show();
                List<DownloadModel> models = new ArrayList<>();
                models.add(new DownloadModel());
                models.add(new DownloadModel());
                models.add(new DownloadModel());
                models.add(new DownloadModel());
                models.add(new DownloadModel());
                TDownloadManager.getInstance().startAll(models);
                break;
            case R.id.btn_pause_all:
                Toast.makeText(getApplicationContext(), "停止全部", Toast.LENGTH_SHORT).show();
                TDownloadManager.getInstance().pauseAll();
                break;
        }
    }

}
