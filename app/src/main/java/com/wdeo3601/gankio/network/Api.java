package com.wdeo3601.gankio.network;


import com.google.gson.JsonObject;
import com.wdeo3601.gankio.base.mvp.data.BaseData;
import com.wdeo3601.gankio.base.mvp.data.DelayData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author/Date: venjerLu / 8/9/2016  18:32 15:56
 * Email:       alwjlola@gmail.com
 * Description:
 */
public interface Api {

    /**
     * 获取发过干货日期接口:
     * @return
     */
    @GET("day/history")
    Observable<BaseData<List<String>>> getUpdateDate();

    /**
     * 获取每日推荐
     *
     * @param date
     * @return
     */
    @GET("day/{date}")
    Observable<DelayData<JsonObject>> getDelayData(@Path("date") String date);

}
