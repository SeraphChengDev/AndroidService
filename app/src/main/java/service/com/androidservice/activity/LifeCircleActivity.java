package service.com.androidservice.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import service.com.androidservice.R;
import service.com.androidservice.service.LifeCircleService;

/**
 * Created by wangchengcheng on 2017/2/6.
 */

public class LifeCircleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnUnbind;
    private Button mBtnBind;
    private Button mBtnStop;
    private Button mBtnStart;
    private boolean mServiceBinded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_life);

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnUnbind = (Button) findViewById(R.id.btn_unbind);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startService();
                break;
            case R.id.btn_stop:
                stopService();
                break;
            case R.id.btn_bind:
                bindService();
                break;
            case R.id.btn_unbind:
                unbindService();
                break;
            default:
                break;
        }
    }

    private void unbindService() {
        if (mServiceBinded) {
            unbindService(mServiceConn);
            mServiceBinded = false;
        }
    }

    private void bindService() {
        Intent intent = new Intent(this, LifeCircleService.class);
        mServiceBinded = bindService(intent, mServiceConn, BIND_AUTO_CREATE);
    }

    private void stopService() {
        Intent intent = new Intent(this, LifeCircleService.class);
        stopService(intent);
    }

    private void startService() {
        Intent intent = new Intent(this, LifeCircleService.class);
        startService(intent);
    }

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
