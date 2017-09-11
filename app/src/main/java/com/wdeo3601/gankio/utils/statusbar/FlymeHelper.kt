package com.wdeo3601.gankio.utils.statusbar

import android.app.Activity
import android.view.Window
import android.view.WindowManager

import java.lang.reflect.Field

/**
 * Created by admin on 2017/3/26.
 */

class FlymeHelper : IHelper {
    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户

     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     * *
     * @return boolean 成功执行返回true
     */
    override fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean {
        val window = activity.window
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (isFontColorDark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return result
    }
}
