package com.wdeo3601.gankio

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.orhanobut.logger.LogLevel
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import com.wdeo3601.gankio.utils.AppStatusTracker
import com.wdeo3601.gankio.utils.DensityHelper
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by wendong on 2017/9/11 0011.
 * Email:       wdeo3601@163.com
 * Description:
 */
class App : MultiDexApplication() {
    var mMainThreadHandler: Handler? = null
    var mMainThreadLooper: Looper? = null
    var mMainThread: Thread? = null
    var mMainThreadId: Long = 0
    @Volatile lateinit var mContext: App
    var mBaseUrlType = "api"

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mMainThreadHandler = Handler()
        mMainThreadLooper = mainLooper
        mMainThread = Thread.currentThread()
        mContext = this@App
        mMainThreadId = android.os.Process.myTid().toLong()

        DensityHelper(this, 375F).activate()
        initBaseUrl()
        AppStatusTracker.init(this@App)
        initRealm()

        Observable.just("")
                .map {
                    initUMeng()
                    initStetho()
                    initLog()
                    LeakCanary.install(mContext)
                    enabledStrictMode()
                    null
                }
                .subscribeOn(Schedulers.io())
                .subscribe()

    }

    private fun initBaseUrl() {
        if (BuildConfig.DEBUG) {
            mBaseUrlType = "beta"
        } else {
            mBaseUrlType = "api"
        }
    }

    private fun initUMeng() {
//        //activity+fragment模式，禁止默认的页面统计方式，这样将不会再自动统计Activity，自己实现fragment的统计
//        MobclickAgent.openActivityDurationTrack(false)
//        //友盟sdk日志加密
//        MobclickAgent.enableEncrypt(true)//6.0.0版本及以后
//        //使用集成测试模式，打开调试模式后，您可以在logcat中查看您的数据是否成功发送到友盟服务器，以及集成过程中的出错原因等，友盟相关log的tag是MobclickAgent。
//        MobclickAgent.setDebugMode(true)
    }

    private fun initRealm() {
        // Initialize Realm. Should only be done once when the application starts.
//        Realm.init(this)
//
//        val config = RealmConfiguration.Builder()
//                //                .deleteRealmIfMigrationNeeded()
//                .schemaVersion(3)//每次realm数据表结构有变动，就需要把realm版本号+1
//                .migration(Migration())
//                .build()
//        Realm.setDefaultConfiguration(config)
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            //            StethoUtils.init(this);
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build())
        }
    }

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Logger.init("gankio").setMethodCount(1).hideThreadInfo().setLogLevel(LogLevel.FULL)
        } else {
            Logger.init("gankio").setMethodCount(0).hideThreadInfo().setLogLevel(LogLevel.NONE)
        }
    }

    private fun enabledStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        }
    }

    /**
     * 设置app中字体不随系统字体大小改变而改变

     * @return
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }
}