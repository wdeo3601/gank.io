package com.wdeo3601.gankio.utils.statusbar

import android.app.Activity
import android.view.Window

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by admin on 2017/3/26.
 */

class MIUIHelper : IHelper {
    /**
     * 设置状态栏字体图标为深色，需要MIUI6以上

     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     * *
     * @return boolean 成功执行返回true
     */
    override fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean {
        val window = activity.window
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (isFontColorDark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                }
                result = true
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return result
    }
}
