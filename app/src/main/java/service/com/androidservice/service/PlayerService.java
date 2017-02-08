/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package service.com.androidservice.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import service.com.androidservice.R;
import service.com.androidservice.utils.LogUtils;

/**
 * Created by wangcheng15 on 2017/2/8.
 */

public class PlayerService extends Service implements PlayCallback {

    private static final String TAG = PlayerService.class.getSimpleName();
    private PlayerBinder mPlayerBinder = new PlayerBinder();
    private MediaPlayer mMediaPlayer;

    public class PlayerBinder extends Binder {

        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(this, R.raw.cnwav);
        LogUtils.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startPlay();
        LogUtils.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(TAG, "onBind");
        return mPlayerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.d(TAG, "onUnbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.d(TAG, "onRebind");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy");
        stopPlay();
        mMediaPlayer.release();
    }

    @Override
    public void startPlay() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void stopPlay() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
