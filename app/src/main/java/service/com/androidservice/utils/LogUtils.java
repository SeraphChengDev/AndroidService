package service.com.androidservice.utils;

import android.util.Log;

import service.com.androidservice.BuildConfig;

/**
 * Created by wangchengcheng on 2017/2/6.
 */

public class LogUtils {


    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }
}
