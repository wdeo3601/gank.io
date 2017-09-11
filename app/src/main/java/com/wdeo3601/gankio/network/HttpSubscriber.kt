package com.wdeo3601.gankio.network

import com.orhanobut.logger.Logger
import com.wdeo3601.gankio.utils.ToastUtil

import java.io.IOException
import java.net.SocketTimeoutException

import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber

/**
 * Author/Date: venjerLu / 8/14/2016 16:32
 * Email:       alwjlola@gmail.com
 * Description:
 */
abstract class HttpSubscriber<T> : Subscriber<T>(), INetResult<T> {

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onCompleted() {
        onComplete()
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        if (e is SocketTimeoutException) {
            Logger.e(TAG, e.message)
        } else if (e is HttpException) {
            val exception = e
            val errorCode = exception.code()
            Logger.e(TAG, errorCode.toString() + "")
            Logger.e(TAG, exception.message() + "")
            if (exception.response() != null && exception.response().errorBody() != null) {
                try {
                    if (errorCode >= 400 && errorCode < 500) {
                        //后台处理的统一格式的错误信息
                        Logger.e(TAG, exception.response().message())
                        val bodyStr = exception.response().errorBody()!!.string()
                        Logger.e(TAG, bodyStr)
                        //                        ErrorModel errorModel = new Gson().fromJson(bodyStr, ErrorModel.class);
                        //                        if (errorModel != null) {
                        //                            if (errorCode == 403) {
                        //                                //用户被封禁
                        //                                exitToIntroActivity();
                        //                            } else {
                        //                                if (errorModel.getErrors().getCode().equals("user not exist")) {
                        //                                    //用户被删除
                        //                                    exitToIntroActivity();
                        //                                } else {
                        //                                    onFail(errorCode, errorModel);
                        //                                }
                        //                            }
                        //                        } else {
                        //                            onDisconnect();
                        //                        }
                    } else {
                        //默认的错误信息
                        onDisconnect()
                    }
                } catch (e1: IOException) {
                    //当获取HttpException里边的数据出错时，方便看到HttpException里的数据
                    e.printStackTrace()

                    e1.printStackTrace()
                }

            }
        } else {
            // 打印异常堆栈，方便查找原因
            e.printStackTrace()
            onDisconnect()
        }
        onComplete()
    }

    //    @Override
    //    public void onFail(int errorCode, ErrorModel errorModel) {
    //        String code = errorModel.getErrors().getCode();
    //        String detail = errorModel.getErrors().getDetail();
    //        String text = Constants.ERRORS.get(code);
    //        String errorStr = TextUtils.isEmpty(detail) ? text : detail;
    //        ToastUtil.showShort(TextUtils.isEmpty(errorStr) ? code : errorStr);
    //    }

    override fun onDisconnect() {
        ToastUtil.showShort("网络错误，请检查您的网络连接是否正常")
    }

    companion object {
        private val TAG = "HttpObserver"
    }
}
