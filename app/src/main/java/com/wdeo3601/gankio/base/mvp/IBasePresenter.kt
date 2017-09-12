package com.wdeo3601.gankio.base.mvp

/**
 * Author/Date: venjerLu / 2016/12/6 23:23
 * Email:       alwjlola@gmail.com
 * Description:
 */

interface IBasePresenter<T : IBaseView> {
    fun attachView(view: T)

    fun detachView()
}
