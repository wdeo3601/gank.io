package com.wdeo3601.gankio.widget

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView

import java.lang.ref.WeakReference

/**
 * Created by lwj on 7/14/2016.
 */
class BaseScrollView : ScrollView {
    var mOnScrollListener: OnScrollListener? = null
    var lastScrollY: Int = 0   //手指离开ScrollView, ScrollView还在继续滑动. 用来保存Y的距离,然后做比较
    var isTop = true
    var isBottom = false
    private var onBorderListener: OnBorderListener? = null
    var contentView: View? = null
    private val mHandler = UIHandler(this)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        mOnScrollListener = onScrollListener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        doOnBorderListener()
    }

    private fun doOnBorderListener() {
        if (contentView != null && contentView!!.measuredHeight <= scrollY + height) {
            isBottom = true
            isTop = false
            if (onBorderListener != null) {
                onBorderListener!!.onBottom()
            }
        } else if (scrollY == 0) {
            isTop = true
            isBottom = false
            if (onBorderListener != null) {
                onBorderListener!!.onTop()
            }
        } else {
            isBottom = false
            isTop = false
        }
    }

    fun setOnBorderListener(onBorderListener: OnBorderListener?) {
        this.onBorderListener = onBorderListener
        if (onBorderListener == null) {
            return
        }

        if (contentView == null) {
            contentView = getChildAt(0)
        }
    }

    interface OnBorderListener {
        /**
         * Called when scroll to bottom
         */
        fun onBottom()

        /**
         * Called when scroll to top
         */
        fun onTop()
    }

    interface OnScrollListener {
        fun onScroll(scrollY: Int)
    }

    private class UIHandler internal constructor(presenter: BaseScrollView) : Handler() {
        private val mWeakReference: WeakReference<BaseScrollView>

        init {
            mWeakReference = WeakReference(presenter)
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mWeakReference.get()!!.handleMsg(msg)
        }
    }

    /**
     * 用于用户手指离开ScrollView的时候获取ScrollView的滚动的Y距离. 然后回调给onScroll
     */
    private fun handleMsg(msg: Message) {
        val scrollY = scrollY

        if (lastScrollY != scrollY) { //此时的距离和之前记录的不等, 在5毫秒给Handler发送消息
            lastScrollY = scrollY
            mHandler.sendMessageDelayed(mHandler.obtainMessage(), 5)
        }
        if (mOnScrollListener != null) {
            mOnScrollListener!!.onScroll(scrollY)
        }
    }

    /**
     * 重写onTouchEvent, 当手指在ScrollView上的时候,直接将滑动的Y方向的距离回调给onScroll()
     * 当手指抬起时,ScrollView可能还在滑动, 所以隔20mm给Handler发信息处理
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        lastScrollY = scrollY
        mOnScrollListener!!.onScroll(scrollY)
        when (ev.action) {
            MotionEvent.ACTION_UP -> mHandler.sendMessageDelayed(mHandler.obtainMessage(), 20)
        }
        return super.onTouchEvent(ev)
    }

    fun scrollToTop() {
        post { fullScroll(ScrollView.FOCUS_UP) }
    }

    fun scrollToBottom() {
        post { fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
