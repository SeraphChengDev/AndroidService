/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package service.com.androidservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Random;

import service.com.androidservice.utils.LogUtils;

/**
 * Created by wangcheng15 on 2017/2/9.
 */

public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    private static final int MSG_RANDOM_NUM = 1000;
    private static final int MSG_SEND_RESPONSE = 1001;

    /**
     * 服务端Messenger
     */
    private Messenger mServiceMessenger = new Messenger(new ServiceMessenger());

    private Random mRandomCreator;

    /**
     * 请求处理器
     */
    private class ServiceMessenger extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RANDOM_NUM:
                    int result = getRandomNum();
                    LogUtils.d(TAG, "Random: " + result);
                    responseClient(msg.replyTo, result);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 返回结果给客户端
     *
     * @param replyTo
     */
    private void responseClient(Messenger replyTo, int result) {
        if (replyTo == null) {
            return;
        }
        Message msg = Message.obtain();
        msg.what = MSG_SEND_RESPONSE;
        msg.arg1 = result;
        try {
            replyTo.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRandomCreator = new Random();
        LogUtils.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(TAG, "onBind");
        return mServiceMessenger.getBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.d(TAG, "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestory");
    }

    /**
     * 获取随机数
     *
     * @return
     */
    public int getRandomNum() {
        return mRandomCreator.nextInt(100);
    }
}
