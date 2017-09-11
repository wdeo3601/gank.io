package com.wdeo3601.gankio.utils.statusbar

import android.app.Activity
import android.os.Build
import android.support.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by admin on 2017/3/26.
 * 适配以下系统的状态栏白底黑字
 * android6.0+
 * MIUI6
 * flyme4+
 */

object Helper {
    @IntDef(OTHER.toLong(), MIUI.toLong(), FLYME.toLong(), ANDROID_M.toLong())
    @Retention(RetentionPolicy.SOURCE)
    annotation class SystemType

    const val OTHER = -1
    const val MIUI = 1
    const val FLYME = 2
    const val ANDROID_M = 3

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android

     * @return 1:MIUI 2:Flyme 3:android6.0
     */
    fun statusBarLightMode(activity: Activity): Int {
        @SystemType var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (MIUIHelper().setStatusBarLightMode(activity, true)) {
                result = MIUI
            } else if (FlymeHelper().setStatusBarLightMode(activity, true)) {
                result = FLYME
            } else if (AndroidMHelper().setStatusBarLightMode(activity, true)) {
                result = ANDROID_M
            }
        }
        return result
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUI6、Flyme和6.0以上版本其他Android

     * @param type 1:MIUI 2:Flyme 3:android6.0
     */
    fun statusBarLightMode(activity: Activity, @SystemType type: Int) {
        statusBarMode(activity, type, true)

    }

    /**
     * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
     */
    fun statusBarDarkMode(activity: Activity, @SystemType type: Int) {
        statusBarMode(activity, type, false)
    }

    private fun statusBarMode(activity: Activity, @SystemType type: Int, isFontColorDark: Boolean) {
        if (type == MIUI) {
            MIUIHelper().setStatusBarLightMode(activity, isFontColorDark)
        } else if (type == FLYME) {
            FlymeHelper().setStatusBarLightMode(activity, isFontColorDark)
        } else if (type == ANDROID_M) {
            AndroidMHelper().setStatusBarLightMode(activity, isFontColorDark)
        }
    }

}
