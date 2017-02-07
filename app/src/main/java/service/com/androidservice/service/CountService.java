package service.com.androidservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by wangchengcheng on 2017/1/18.
 */

public class CountService extends Service implements ICountService {

    private boolean mInterrupt;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private final IBinder mBinder = new CountBinder();


    public class CountBinder extends Binder {

        public CountService getService() {
            return CountService.this;
        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void startCount(final int initVal, final ICountCallback callback) {
        final AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {

            @Override
            protected Integer doInBackground(Integer... params) {
                mInterrupt = false;
                int val = params[0];
                try {
                    while (!mInterrupt) {
                        publishProgress(val);

                        Thread.sleep(1000);
                        val++;

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return val;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                callback.countCallback(integer);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                callback.countCallback(values[0]);
            }
        };
        task.execute(initVal);

    }

    @Override
    public void stopCount() {
        mInterrupt = true;
    }


}
