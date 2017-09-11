package com.wdeo3601.gankio.network

import com.orhanobut.logger.Logger

import rx.Subscriber

/**
 * Created by lwj on 8/26/2016.
 */
abstract class BaseSubscriber<T> : Subscriber<T>() {

    override fun onCompleted() {
        onComplete()
    }

    override fun onError(e: Throwable) {
        onFail(e.toString())
        onComplete()
    }

    protected fun onFail(error: String) {
        Logger.d(TAG, error)
    }

    protected fun onComplete() {

    }

    companion object {
        private val TAG = "BaseSubscriber"
    }
}
