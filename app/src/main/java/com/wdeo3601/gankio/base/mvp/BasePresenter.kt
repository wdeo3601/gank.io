package com.wdeo3601.gankio.base.mvp

import com.wdeo3601.gankio.network.Api
import com.wdeo3601.gankio.network.NetService

/**
 * Author/Date: venjerLu / 8/11/2016 16:53
 * Email:       alwjlola@gmail.com
 * Description:
 */
class BasePresenter<V : IBaseView>(protected var mView: V?) {

    fun onCreate() {}

    fun onDestroy() {
        this.mView = null
    }

    protected fun baseApi(): Api {
        return NetService.getInstance().api
    }
}
