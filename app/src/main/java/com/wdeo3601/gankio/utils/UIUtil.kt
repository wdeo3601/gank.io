package com.wdeo3601.gankio.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast

import com.wdeo3601.gankio.App

object UIUtil {

    /**
     * 获取上下文
     */
    val context: Context
        get() = App.instance.mContext

    /**
     * 获取主线程
     */
    val mainThread: Thread
        get() = App.instance.mMainThread!!

    /**
     * 获取主线程id
     */
    val mainThreadId: Long
        get() = App.instance.mMainThreadId

    /**
     * 获取到主线程的looper
     */
    val mainThreadLooper: Looper
        get() = App.instance.mMainThreadLooper!!

    /**
     * 获取主线程的handler
     */
    val handler: Handler
        get() = App.instance.mMainThreadHandler!!

    /**
     * 延时在主线程执行runnable
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long): Boolean {
        return handler.postDelayed(runnable, delayMillis)
    }

    /**
     * 在主线程执行runnable
     */
    fun post(runnable: Runnable): Boolean {
        return handler.post(runnable)
    }

    /**
     * 从主线程looper里面移除runnable
     */
    fun removeCallbacks(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }

    /**
     * 获取布局
     */
    fun inflate(resId: Int): View {
        return LayoutInflater.from(context).inflate(resId, null)
    }

    /**
     * 获取资源
     */
    val resources: Resources
        get() = context.resources

    /**
     * 获取文字
     */
    fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    /**
     * 获取文字数组
     */
    fun getStringArray(resId: Int): Array<String> {
        return resources.getStringArray(resId)
    }

    /**
     * 获取dimen
     */
    fun getDimens(resId: Int): Int {
        return resources.getDimensionPixelSize(resId)
    }

    /**
     * 获取drawable
     */
    fun getDrawable(resId: Int): Drawable {
        return resources.getDrawable(resId)
    }

    /**
     * 获取颜色
     */
    fun getColor(resId: Int): Int {
        return resources.getColor(resId)
    }

    /**
     * 获取颜色选择器
     */
    fun getColorStateList(resId: Int): ColorStateList {
        return resources.getColorStateList(resId)
    }

    // 判断当前的线程是不是在主线程
    val isRunInMainThread: Boolean
        get() = android.os.Process.myTid().toLong() == mainThreadId

    fun runInMainThread(runnable: Runnable) {
        // 在主线程运行
        if (isRunInMainThread) {
            runnable.run()
        } else {
            post(runnable)
        }
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    fun showToastSafe(resId: Int) {
        showToastSafe(getString(resId))
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    fun showToastSafe(str: String) {
        if (isRunInMainThread) {
            showToast(str)
        } else {
            post(Runnable { showToast(str) })
        }
    }

    private fun showToast(str: String) {
        if (AppStatusTracker.instance!!.isForeground) {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(dpValue: Float): Int {
        val scale = App.instance.mContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(pxValue: Float): Int {
        val scale = App.instance.mContext.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 spValue(像素) 的单位 转成为 px
     */
    fun sp2px(spValue: Float): Int {
        val scale = App.instance.mContext.resources.displayMetrics.scaledDensity
        return (spValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    fun px2sp(pxValue: Float): Int {
        val scale = App.instance.mContext.resources.displayMetrics.scaledDensity
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 转换pt为px

     * @param context
     * *
     * @param value   需要转换的pt值，若context.resources.displayMetrics经过resetDensity()的修改则得到修正的相对长度，否则得到原生的磅
     * *
     * @return
     */
    fun pt2px(context: Context, value: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, value, context.resources.displayMetrics).toInt()
    }

    /**
     * 获取屏幕的高度 单位/px
     */
    fun getScreenHeight(activity: Activity): Int {
        return getDisplayMetrics(activity).heightPixels
    }

    /**
     * 获取屏幕的高度 单位/px
     */
    fun getScreenWidth(activity: Activity): Int {
        return getDisplayMetrics(activity).widthPixels
    }

    fun getDisplayMetrics(activity: Activity): DisplayMetrics {
        val outMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics
    }
}
