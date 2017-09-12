package com.wdeo3601.gankio.base.mvp

/**
 * Author/Date: venjerLu / 2016/11/24 10:07
 * Email:       alwjlola@gmail.com
 * Description:
 */
interface IErrorView : IBaseView {
    fun retryConnectToInternet()

    fun showError(show: Boolean)
}
