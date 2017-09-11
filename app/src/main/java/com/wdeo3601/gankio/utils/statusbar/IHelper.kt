package com.wdeo3601.gankio.utils.statusbar

import android.app.Activity

/**
 * Created by admin on 2017/3/26.
 */

interface IHelper {
    fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean
}
