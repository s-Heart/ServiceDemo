package demo.com.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tony.downloadlib.LocalService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_local_service).setOnClickListener(this);
        findViewById(R.id.btn_bind_local_service).setOnClickListener(this);
        findViewById(R.id.btn_stop_local_service).setOnClickListener(this);

        findViewById(R.id.btn_start_bind_service).setOnClickListener(this);
        findViewById(R.id.btn_bind_start_service).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_start_local_service:
                intent = new Intent(getApplicationContext(), LocalService.class);
                startService(intent);
                break;
            case R.id.btn_bind_local_service:
                bindService(new Intent(getApplicationContext(), LocalService.class), MainActivity.this, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_local_service:
                stopService(new Intent(getApplicationContext(), LocalService.class));
                break;
            case R.id.btn_start_bind_service:
                intent = new Intent(getApplicationContext(), LocalService.class);
                startService(intent);
                bindService(intent, MainActivity.this, BIND_AUTO_CREATE);
                break;
            case R.id.btn_bind_start_service:
                intent = new Intent(getApplicationContext(), LocalService.class);
                bindService(intent, MainActivity.this, BIND_AUTO_CREATE);
                startService(intent);
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("=====", "onServiceConnected: " + iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("=====", "onServiceDisconnected: " + componentName);
    }
}
