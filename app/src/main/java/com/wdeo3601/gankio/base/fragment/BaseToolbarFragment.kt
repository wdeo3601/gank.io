package com.wdeo3601.gankio.base.fragment

import android.os.Build
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView

import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.base.activity.BaseActicity
import com.wdeo3601.gankio.utils.AndroidUtil
import com.wdeo3601.gankio.utils.UIUtil


abstract class BaseToolbarFragment : BaseLazyMainFragment() {
    protected lateinit var mAppbar: AppBarLayout
    protected lateinit var mToolbar: Toolbar
    protected lateinit var mTitle: TextView
    protected var _isHidden = false
    private var mTitleDeviderLine: View? = null

    override fun initToolbar(view: View?) {
        mToolbar = view!!.findViewById<View>(R.id.toolbar) as Toolbar
        mTitle = view.findViewById<View>(R.id.textView_title) as TextView
        mAppbar = view.findViewById<View>(R.id.app_bar_layout) as AppBarLayout
        mTitleDeviderLine = view.findViewById(R.id.title_devider_line)
        mToolbar.fitsSystemWindows = false
        initToolbarNav(mToolbar)
        initToolbarMenu(mToolbar)
        adjustToolbar()
    }

    /**
     * 调整 toolbar，因为 MainActivity 设置为了布局引申到状态栏。
     */
    private fun adjustToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = _mActivity.window
            val systemUiVisibility = window.decorView.systemUiVisibility
            if (systemUiVisibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE) {
                mToolbar.titleMarginTop = UIUtil.dp2px(AndroidUtil.getTypedValueFromAttr(_mActivity, R.attr.actionBarSize).data.toFloat())
            }
        }
    }

    /**
     * 设置标题
     */
    protected fun setTitle(res: Int) {
        mTitle.setText(res)
    }

    protected fun setToolbarTitle(title: String) {
        mToolbar.title = title
    }

    protected fun setTitle(s: String?) {
        if (s != null) {
            mTitle.text = Html.fromHtml(s)
        }
    }

    protected open fun initToolbarNav(toolbar: Toolbar) {}

    /**
     * 初始化 toolbar 的 menu
     */
    protected fun initToolbarMenu(toolbar: Toolbar) {
        if (menuId != -1) {
            toolbar.inflateMenu(menuId)
            toolbar.setOnMenuItemClickListener { item ->
                menuItemClick(item.itemId)
                true
            }
        }
    }

    protected val menuId: Int
        get() = -1

    protected fun hideOrShowToolbar() {
        mAppbar.animate()
                .translationY((if (_isHidden) 0 else -mAppbar.height).toFloat())
                .setInterpolator(DecelerateInterpolator(2f))
                .start()
        _isHidden = !_isHidden
    }

    /**
     * 在 5.0 以上时如果是沉浸式状态栏需要调整 toolbar
     */
    protected fun adjustToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val statusBarHeight = (_mActivity as BaseActicity).getStatusBarHeight()
            val layoutParams = mToolbar.layoutParams as AppBarLayout.LayoutParams
            layoutParams.setMargins(0, statusBarHeight, 0, 0)
            mToolbar.layoutParams = layoutParams
        }
    }

    /**
     * 设置是否显示标题栏的分割线
     *
     * @param visiable
     */
    protected fun setTitleDeviderLineVisiable(visiable: Boolean) {
        if (mTitleDeviderLine == null) {
            return
        }
        if (visiable) {
            mTitleDeviderLine!!.visibility = View.VISIBLE
        } else {
            mTitleDeviderLine!!.visibility = View.GONE
        }
    }

}