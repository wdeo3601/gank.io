package com.wdeo3601.gankio.base.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.wdeo3601.gankio.base.mvp.IBaseView
import com.wdeo3601.gankio.event.EmptyEvent
import com.wdeo3601.gankio.network.Api
import com.wdeo3601.gankio.network.NetService
import com.wdeo3601.gankio.utils.MaterialDialogUtil
import com.wdeo3601.gankio.utils.ToastUtil
import com.wdeo3601.gankio.utils.UIUtil
import com.wdeo3601.gankio.widget.BaseScrollView
import me.yokeyword.fragmentation.ISupportFragment

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import me.yokeyword.fragmentation.SupportFragment
import permissions.dispatcher.PermissionRequest
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Author/Date: venjerLu / 2016/5/24 23:26
 * Email:       alwjlola@gmail.com
 * Description:
 */
abstract class BaseFragment : SupportFragment(), IBaseView {
    var isOverrideOnBackPressedSupport: Boolean = false
    protected var mProgressDialog: MaterialDialog? = null
    protected lateinit var mHandler: Handler
    private var mCompositeSubscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return if (layoutId != -1) {
            inflater!!.inflate(layoutId, container, false)
        } else null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EventBus.getDefault().register(this)
        mHandler = UIUtil.handler
        initToolbar(view)
        initView(view)
        initData(savedInstanceState)
    }

    val compositeSubscription: CompositeSubscription
        get() {
            if (this.mCompositeSubscription == null) {
                this.mCompositeSubscription = CompositeSubscription()
            }

            return this.mCompositeSubscription!!
        }

    override fun addSubscription(s: Subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }

        this.mCompositeSubscription!!.add(s)
    }

    open fun initToolbar(view: View?) {}

    protected fun filterException(e: Exception?): Boolean {
        if (e != null) {
            e.printStackTrace()
            ToastUtil.showShort(e.message!!)
            return false
        } else {
            return true
        }
    }

    protected abstract val layoutId: Int

    protected abstract fun initView(rootView: View?)

    protected abstract fun initData(savedInstanceState: Bundle?)

    protected fun menuItemClick(itemId: Int) {

    }

    // 给多个view设置水波纹
    protected fun setRipple(vararg views: View) {
        for (i in views.indices) {
            setRipple(views[i])
        }
    }

    protected fun showDialog() {
        showDialog("加载中...")
    }

    override fun showDialog(text: String) {
        if (mProgressDialog == null) {
            mProgressDialog = MaterialDialogUtil.showProgressIndeterminateDialog(_mActivity, text)
        } else {
            mProgressDialog!!.setContent(text)
            if (!mProgressDialog!!.isShowing) mProgressDialog!!.show()
        }
    }

    protected fun dismissDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog!!.isShowing) mProgressDialog!!.dismiss()
        }
    }

    @Subscribe
    fun onEvent(emptyEvent: EmptyEvent) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
        //        GlideFaceDetector.releaseDetector();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription!!.unsubscribe()
        }
        mHandler.removeCallbacks(null)
    }

    protected fun setViewVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    protected fun baseApi(): Api {
        return NetService.getInstance().api
    }

    /**
     * @param root
     * @param scrollView
     */
    protected fun haveViewScrollToBottom(root: View, scrollView: BaseScrollView) {
        root.viewTreeObserver
                .addOnGlobalLayoutListener {
                    val heightDiff = scrollView.rootView.height - root.height
                    if (heightDiff > UIUtil.dp2px(200f)) { //如果view的高度大于200dp,那么很有可能就是键盘
                        scrollView.scrollToBottom()
                    } else {
                        scrollView.scrollToTop()
                    }
                }
    }
    //
    //    protected void hiddenKeyboard(final View root, final View scrollView) {
    //        root.getViewTreeObserver()
    //                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    //                    @Override
    //                    public void onGlobalLayout() {
    //                        int heightDiff = scrollView.getRootView().getHeight() - root.getHeight();
    //                        if (heightDiff > UIUtil.INSTANCE.dp2px(200)) { //如果view的高度大于200dp,那么很有可能就是键盘
    //                            KeyBoardUtil.hideKeyboard(root);
    //                        }
    //                    }
    //                });
    //    }

    override fun setLoadingView(show: Boolean) {
        if (show) {
            showDialog()
        } else {
            dismissDialog()
        }
    }

    override fun startFragment(fragment: SupportFragment) {
        start(fragment, ISupportFragment.SINGLETASK)
    }

    override val viewContext: Context
        get() = _mActivity

    protected fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        MaterialDialog.Builder(_mActivity).positiveText("允许")
                .onPositive { dialog, which -> request.proceed() }
                .negativeText("拒绝")
                .onNegative { dialog, which -> request.cancel() }
                .content(messageResId)
                .cancelable(false)
                .build()
                .show()
    }

    companion object {
        private val TAG = "Fragmentation"
    }
}
