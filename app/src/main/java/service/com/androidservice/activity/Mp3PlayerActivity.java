/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
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
import service.com.androidservice.service.PlayerService;

/**
 * Created by wangcheng15 on 2017/2/8.
 */

public class Mp3PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnUnbind;
    private Button mBtnBind;
    private PlayerService mMp3Service;
    private boolean mBindedService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnUnbind = (Button) findViewById(R.id.btn_unbind);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);

        Intent intent = new Intent(this, PlayerService.class);
        mBindedService = bindService(intent, mPlayerConn, BIND_AUTO_CREATE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startPlay();
                break;
            case R.id.btn_stop:
                stopPlay();
                break;
            case R.id.btn_bind:
                bindPlay();
                break;
            case R.id.btn_unbind:
                unBindPlay();
                break;
            default:
                break;
        }
    }

    private void unBindPlay() {
        if (mMp3Service != null) {
            mMp3Service.stopPlay();
        }
    }

    private void bindPlay() {
        if (mMp3Service != null) {
            mMp3Service.startPlay();
        }
    }

    private void stopPlay() {
        Intent intent = new Intent(this, PlayerService.class);
        stopService(intent);
    }

    private void startPlay() {
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
    }

    private ServiceConnection mPlayerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMp3Service = ((PlayerService.PlayerBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMp3Service = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBindedService) {
            unbindService(mPlayerConn);
        }
    }
}
