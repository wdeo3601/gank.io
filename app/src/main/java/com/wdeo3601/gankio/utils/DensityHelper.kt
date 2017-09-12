package com.wdeo3601.gankio.utils

/**
 * Created by wendong on 2017/9/1 0001.
 * Email:       wdeo3601@163.com
 * Description:
 */

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

import java.lang.reflect.Field

import android.content.Context.WINDOW_SERVICE

/**
 * Created by Caodongyao on 2017/8/4.
 */

class DensityHelper
/**
 * @param application
 * *
 * @param width       设计稿宽度
 */
(private val mApplication: Application, width: Float) {


    private val activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks
    private var designWidth = 720f

    init {
        designWidth = width

        activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //通常情况下application与activity得到的resource虽然不是一个实例，但是displayMetrics是同一个实例，只需调用一次即可
                //为了面对一些不可预计的情况以及向上兼容，分别调用一次较为保险
                resetDensity(mApplication, designWidth)
                resetDensity(activity, designWidth)
            }

            override fun onActivityStarted(activity: Activity) {
                resetDensity(mApplication, designWidth)
                resetDensity(activity, designWidth)
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        }
    }

    /**
     * 激活本方案
     */
    fun activate() {
        resetDensity(mApplication, designWidth)
        mApplication.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    /**
     * 恢复系统原生方案
     */
    fun inactivate() {
        restoreDensity(mApplication)
        mApplication.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    companion object {

        /**
         * 重新计算displayMetrics.xhdpi, 使单位pt重定义为设计稿的相对长度

         * @param context
         * *
         * @param designWidth 设计稿的宽度
         * *
         * @see .activate
         */
        fun resetDensity(context: Context?, designWidth: Float) {
            if (context == null)
                return

            val size = Point()
            (context.getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)

            val resources = context.resources

            resources.displayMetrics.xdpi = size.x / designWidth * 72f

            //解决MIUI更改框架导致的MIUI7+Android5.1.1上出现的失效问题(以及极少数基于这部分miui去掉art然后置入xposed的手机)
            if ("MiuiResources" == resources.javaClass.simpleName || "XResources" == resources.javaClass.simpleName) {
                try {
                    val field = Resources::class.java.getDeclaredField("mTmpMetrics")
                    field.isAccessible = true
                    val metrics = field.get(resources) as DisplayMetrics
                    metrics.xdpi = size.x / designWidth * 72f

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        /**
         * 恢复displayMetrics为系统原生状态，单位pt恢复为长度单位磅

         * @param context
         * *
         * @see .inactivate
         */
        private fun restoreDensity(context: Context) {
            context.resources.displayMetrics.setToDefaults()
        }

        /**
         * 转换dp为px

         * @param context
         * *
         * @param value   需要转换的dp值
         * *
         * @return
         */
        fun dp2px(context: Context, value: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
        }

        /**
         * 转换pt为px

         * @param context
         * *
         * @param value   需要转换的pt值，若context.resources.displayMetrics经过resetDensity()的修改则得到修正的相对长度，否则得到原生的磅
         * *
         * @return
         */
        fun pt2px(context: Context, value: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, value, context.resources.displayMetrics)
        }
    }


}
