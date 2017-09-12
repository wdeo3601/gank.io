package com.wdeo3601.gankio.network;


import com.wdeo3601.gankio.base.mvp.data.DelayData;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author/Date: venjerLu / 8/9/2016  18:32 15:56
 * Email:       alwjlola@gmail.com
 * Description:
 */
public interface Api {
    /**
     * 获取每日推荐
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DelayData<String>> getDelayData(@Path("year") int year
            , @Path("month") int month
            , @Path("day") int day);
}
