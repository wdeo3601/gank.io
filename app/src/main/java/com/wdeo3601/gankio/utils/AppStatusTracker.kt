package com.wdeo3601.gankio.utils

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle

import com.orhanobut.logger.Logger
import com.wdeo3601.gankio.constant.Constants


/**
 * Created by luwenjie on 2016/5/5.
 */
class AppStatusTracker(private val mApplication: Application) : Application.ActivityLifecycleCallbacks {
    //在线状态时 就注册广播
    //其他情况下 不需要广播 若是广播还在 则需要注销
    var appstatus = Constants.STATUS_FORCE_KILLED
        set(appstatus) {
            field = appstatus
            if (appstatus == Constants.STATUS_ONLINE) {
                if (mReceiver == null) {
                    val filter = IntentFilter()
                    filter.addAction(Intent.ACTION_SCREEN_OFF)
                    mReceiver = DaemonReceiver()
                    mApplication.registerReceiver(mReceiver, filter)
                }
            } else if (mReceiver != null) {
                mApplication.unregisterReceiver(mReceiver)
                mReceiver = null
            }
        }
    var isForeground: Boolean = false
    private var activeCount: Int = 0
    private var timestamp: Long = 0
    var isScreenOff: Boolean = false
    private var mReceiver: DaemonReceiver? = null
    var topActivity: Activity? = null
        private set

    init {
        mApplication.registerActivityLifecycleCallbacks(this)
    }

    /**
     * 检查是否要显示解锁的手势
     */
    fun checkIfShowGesture(): Boolean {
        if (appstatus == Constants.STATUS_ONLINE) {
            if (isScreenOff) {
                return true
            }
            if (System.currentTimeMillis() - timestamp > MAX_INTERVAL) {
                return true
            }
        }
        return false
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle) {}

    override fun onActivityStarted(activity: Activity) {
        if (activeCount == 0) {
            timestamp = System.currentTimeMillis()
        }
        activeCount++
    }

    override fun onActivityResumed(activity: Activity) {
        topActivity = activity
        isForeground = true
        isScreenOff = false
    }

    override fun onActivityPaused(activity: Activity) {
        topActivity = null
    }

    override fun onActivityStopped(activity: Activity) {
        activeCount--
        if (activeCount == 0) {
            isForeground = false
            timestamp = System.currentTimeMillis() - timestamp
            Logger.e((timestamp / 1000).toString() + "s")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {}

    private inner class DaemonReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (Intent.ACTION_SCREEN_OFF == action) {
                AppStatusTracker.instance!!.isScreenOff = true
            }
        }
    }

    companion object {
        private val MAX_INTERVAL = (5 * 60 * 1000).toLong()
        var instance: AppStatusTracker? = null
            private set

        fun init(application: Application) {
            instance = AppStatusTracker(application)
        }
    }
}
