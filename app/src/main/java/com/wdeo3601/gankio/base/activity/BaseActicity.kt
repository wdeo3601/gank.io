package com.wdeo3601.gankio.base.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.orhanobut.logger.Logger
import com.wdeo3601.gankio.modules.MainActivity
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.constant.Constants
import com.wdeo3601.gankio.event.EmptyEvent
import com.wdeo3601.gankio.network.Api
import com.wdeo3601.gankio.network.NetService
import com.wdeo3601.gankio.utils.AppStatusTracker
import com.wdeo3601.gankio.utils.MaterialDialogUtil
import com.wdeo3601.gankio.utils.UIUtil
import com.wdeo3601.gankio.utils.statusbar.Helper
import com.wdeo3601.gankio.widget.BaseScrollView
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import permissions.dispatcher.PermissionRequest
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by wendong on 2017/9/11 0011.
 * Email:       wdeo3601@163.com
 * Description:
 */
abstract class BaseActicity : SupportActivity() {
    protected var mProgressDialog: MaterialDialog? = null
    private var mCompositeSubscription: CompositeSubscription? = null
    private lateinit var mContext: Context
    protected var statusBarcolor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (AppStatusTracker.instance?.appstatus) {
            Constants.STATUS_FORCE_KILLED -> protectApp()
            Constants.STATUS_KICK_OUT -> KickOut()
            Constants.STATUS_LOGOUT, Constants.STATUS_OFFLINE, Constants.STATUS_ONLINE -> {
                if (getLayoutId() != 0) {
                    if (isFullScreen()) {
                        requestWindowFeature(Window.FEATURE_NO_TITLE)
                        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    }
                    if (resetTheme()) setTheme(R.style.AppTheme)

                    setContentView(getLayoutId())
                }
                EventBus.getDefault().register(this)
//                GlideFaceDetector.initialize(this)
                mContext = this
                if (Helper.statusBarLightMode(this) === 0) {
                    //如果状态栏字体变色失败，就用半透明状态栏，避免白色状态栏白色字体
                    statusBarcolor = UIUtil.getColor(R.color.colorPrimary)
                } else {
                    statusBarcolor = UIUtil.getColor(R.color.white)
                }
                initView()
                initData(savedInstanceState)
            }
        }
    }

    protected fun resetTheme(): Boolean {
        return true
    }

    protected fun isFullScreen(): Boolean {
        return false
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initData(savedInstanceState: Bundle?)

    protected fun KickOut() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.KEY_HOME_ACTION, Constants.ACTION_KICK_OUT)
        startActivity(intent)
    }

    //app 被强杀后为了保证数据不丢失 重新走一遍登陆流程。也可以自己恢复处理数据，但是比较繁琐
    protected fun protectApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.KEY_HOME_ACTION, Constants.ACTION_RESTART_APP)
        startActivity(intent)
    }

    /**
     * 将布局延伸至状态栏
     */
    protected fun extendToStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            //            window.addFlags(Window.FEATURE_ACTION_BAR_OVERLAY);
            //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 取消布局延伸至状态栏
     */
    protected fun clearExtendToStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = statusBarcolor
        }
    }

    //过滤异常
    protected fun filterException(e: Exception?): Boolean {
        if (e != null) {
            e.printStackTrace()
            Logger.e(e.message)
            return false
        } else {
            return true
        }
    }

    @Subscribe
    fun onEvent(emptyEvent: EmptyEvent) {

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置全局动画
        return DefaultVerticalAnimator()
    }

    // 给多个view设置水波纹
    protected fun setRipple(vararg views: View) {
        for (view in views) {
            setRipple(view)
        }
    }

    fun getCompositeSubscription(): CompositeSubscription {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }

        return this.mCompositeSubscription!!
    }

    fun addSubscription(s: Subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }
        this.mCompositeSubscription!!.add(s)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription!!.unsubscribe()
        }
    }

    protected fun setViewVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    protected fun showDialog() {
        showDialog("加载中...")
    }

    fun showDialog(text: String) {
        if (mProgressDialog == null) {
            mProgressDialog = MaterialDialogUtil.showProgressIndeterminateDialog(this, text)
        } else {
            mProgressDialog!!.setContent(text)
            mProgressDialog!!.show()
        }
    }

    protected fun dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    protected fun baseApi(): Api {
        return NetService.getInstance().getApi()
    }

    /**
     * 得到状态栏的高度
     */
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * @param root
     * *
     * @param scrollView
     */
    protected fun haveViewScrollToBottom(root: View, scrollView: BaseScrollView) {
        root.viewTreeObserver
                .addOnGlobalLayoutListener {
                    val heightDiff = scrollView.getRootView().getHeight() - root.height
                    if (heightDiff > UIUtil.dp2px(200F)) { //如果view的高度大于200dp,那么很有可能就是键盘
                        scrollView.scrollToBottom()
                    } else {
                        scrollView.scrollToTop()
                    }
                }
    }

    fun setLoadingView(show: Boolean) {
        if (show) {
            showDialog()
        } else {
            dismissDialog()
        }
    }

    fun startFragment(fragment: SupportFragment) {
        start(fragment, SupportFragment.SINGLETASK)
    }

    fun getViewContext(): Context {
        return mContext
    }

    protected fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        showRationaleDialog(messageResId, request, MaterialDialog.SingleButtonCallback{ materialDialog: MaterialDialog, dialogAction: DialogAction ->request.cancel()})
    }

    protected fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest, negativeCallback: MaterialDialog.SingleButtonCallback) {
        MaterialDialog.Builder(this).positiveText("允许")
                .onPositive({ materialDialog: MaterialDialog, dialogAction: DialogAction ->request.proceed()})
                .negativeText("拒绝")
                .onNegative(negativeCallback)
                .content(messageResId)
                .cancelable(false)
                .build()
                .show()
    }

    /**
     * 检查是否传入的权限全部允许
     */
    protected fun checkPermissions(vararg permissions: String): Boolean {
        for (permission in permissions) {
            val granted = ContextCompat.checkSelfPermission(this, permission)
            if (granted != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    /**
     * 检查版本更新
     */
    fun checkBetaUpdate() {
//        val appVersionCode = AppUtils.getAppVersionCode(getViewContext())
//        if (AppUtils.isAppDebug(this)) {
//            //debug版（内测版）
//            checkDebugUpdate(appVersionCode)
//        } else {
//            //release版（正式版）
//            checkReleaseUpdate(appVersionCode)
//        }

    }

}