package com.wdeo3601.gankio.base.fragment

import android.support.v7.widget.Toolbar
import android.view.View

import com.wdeo3601.gankio.R

import org.greenrobot.eventbus.EventBus


abstract class BaseBackFragment : BaseToolbarFragment() {
    override fun initToolbarNav(toolbar: Toolbar) {
        if (isBack) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            toolbar.setNavigationOnClickListener { _mActivity.onBackPressed() }
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()

    }

    protected val isBack: Boolean
        get() = true

    protected fun needHideBottomBar(): Boolean {
        return true
    }

    override fun onBackPressedSupport(): Boolean {
        hideSoftInput()
        return super.onBackPressedSupport()
    }
}
