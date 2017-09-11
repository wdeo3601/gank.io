package com.wdeo3601.gankio.network;


/**
 * Created by lwj on 8/14/2016.
 */
public interface INetResult<T> {
//    void onFail(int errorCode, ErrorModel model);

    void onSuccess(T model);

    void onComplete();

    void onDisconnect();
}
