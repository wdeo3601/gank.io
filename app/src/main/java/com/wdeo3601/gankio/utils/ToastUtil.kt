package com.wdeo3601.gankio.utils

import android.content.Context
import android.widget.Toast

import com.wdeo3601.gankio.App


object ToastUtil {

    private fun show(context: Context, resId: Int, duration: Int) {
        Toast.makeText(context, resId, duration).show()
    }

    private fun show(context: Context, message: String, duration: Int) {
        Toast.makeText(context, message, duration).show()
    }

    fun showShort(resId: Int) {
        show(App.instance.mContext, resId, Toast.LENGTH_SHORT)
    }

    fun showShort(message: String) {
        show(App.instance.mContext, message, Toast.LENGTH_SHORT)
    }

    fun showLong(resId: Int) {
        show(App.instance.mContext, resId, Toast.LENGTH_LONG)
    }

    fun showLong(message: String) {
        show(App.instance.mContext, message, Toast.LENGTH_LONG)
    }

    fun showLongLong(message: String) {
        showLong(message)
        showLong(message)
    }

    fun showLongLong(resId: Int) {
        showLong(resId)
        showLong(resId)
    }

    fun showLongLongLong(resId: Int) {
        showLong(resId)
        showLong(resId)
        showShort(resId)
    }

    fun showLongLongLong(message: String) {
        showLong(message)
        showLong(message)
        showShort(message)
    }
}
