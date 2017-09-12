package com.wdeo3601.gankio.base.fragment

import android.view.View
import android.widget.Button
import android.widget.FrameLayout

import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.base.mvp.IErrorView


abstract class BaseErrorListFragment<T> : BaseListFragment<T>(), IErrorView {
    private var mErrorView: FrameLayout? = null

    override fun initView(rootView: View?) {
        super.initView(rootView)
        mErrorView = rootView!!.findViewById<View>(R.id.view_error) as FrameLayout
        val mRetry = mErrorView!!.findViewById<View>(R.id.view_error_retry) as Button
        mRetry.setOnClickListener { retryConnectToInternet() }
    }

    override fun showError(show: Boolean) {
        mErrorView!!.visibility = if (show) View.VISIBLE else View.GONE
    }

    protected val isError: Boolean
        get() = mErrorView!!.visibility == View.VISIBLE
}
