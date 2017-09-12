
package com.wdeo3601.gankio.network;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wdeo3601.gankio.BuildConfig;
import com.wdeo3601.gankio.constant.Constants;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author/Date: venjerLu / 8/9/2016 10:06
 * Email:       alwjlola@gmail.com
 * Description:
 */
public class NetService {
    private static final String TAG = "NetService";
    private volatile static NetService sNetService;
    private Retrofit mRetrofit;
    private Api mApi;

    public static NetService getInstance() {
        if (sNetService == null) {
            synchronized (NetService.class) {
                if (sNetService == null) {
                    sNetService = new NetService();
                }
            }
        }
        return sNetService;
    }

    private NetService() {
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
                    throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .method(original.method(), original.body())
                        .header("Content-Type", "application/json");
//                if (!TextUtils.isEmpty(RxSPManager.getInstance().getPreferenceToken().get())) {
//                    if (!RxSPManager.getInstance().getPreferenceToken().get().equals("Qxt ")) {
//                        builder.header("Authorization", RxSPManager.getInstance().getPreferenceToken().get());
//                    }
//                }
//                builder.header("Accept",Constants.ACCEPT);//api版本
                Request finalRequest = builder.build();
                HttpUrl url = finalRequest.url()
                        .newBuilder()
//                        .addQueryParameter("udid", AndroidUtil.getDeviceId())
                        .build();
                Request build = finalRequest.newBuilder().url(url).build();
                return chain.proceed(build);
            }
        }).sslSocketFactory(sslContext.getSocketFactory(), xtm).hostnameVerifier(DO_NOT_VERIFY).build();
        if (BuildConfig.DEBUG) {
            // Log 信息拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(httpLoggingInterceptor);
        }

    httpClient.networkInterceptors().add(new StethoInterceptor());
//        StethoUtils.configureInterceptor(httpClient);
        OkHttpClient okHttpClient = httpClient
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        mRetrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl(Constants.INSTANCE.getBASE_URL())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Api getApi() {
        if (mApi == null) mApi = mRetrofit.create(Api.class);
        return mApi;
    }

    public <T> T CreateApi() {
        ParameterizedType parameterizedType =
                (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> tClass = (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
        return mRetrofit.create(tClass);
    }
}