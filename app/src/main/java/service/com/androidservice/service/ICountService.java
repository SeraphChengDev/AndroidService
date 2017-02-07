package service.com.androidservice.service;

/**
 * Created by wangchengcheng on 2017/1/18.
 */

public interface ICountService {

    void startCount(int initVal, ICountCallback callback);

    void stopCount();
}
