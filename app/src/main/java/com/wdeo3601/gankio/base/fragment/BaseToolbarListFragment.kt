package com.wdeo3601.gankio.base.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.widget.pulltorefresh.EnhancedRecyclerView
import com.wdeo3601.gankio.widget.pulltorefresh.GeneralRecyclerViewAdapter
import com.wdeo3601.gankio.widget.pulltorefresh.decoration.DividerItemDecoration
import com.wdeo3601.gankio.widget.pulltorefresh.layoutManager.BaseLinearLayoutManager
import com.wdeo3601.gankio.widget.pulltorefresh.layoutManager.ILayoutManager
import java.util.ArrayList

/**
 * Created by wendong on 2017/9/14 0014.
 * Email:       wdeo3601@163.com
 * Description:
 */
abstract class BaseToolbarListFragment : BaseToolbarFragment(), EnhancedRecyclerView.OnRecyclerRefreshListener {
    protected lateinit var mList: MutableList<Any>
    protected lateinit var mRecyclerView: RecyclerView

    protected lateinit var enhancedRecyclerView: EnhancedRecyclerView

    override val layoutId: Int
        get() = R.layout.fragment_base_back_list

    override fun initView(rootView: View?) {
        enhancedRecyclerView = rootView!!.findViewById<View>(R.id.enhancedRecyclerView) as EnhancedRecyclerView
        mRecyclerView = enhancedRecyclerView.recyclerView
    }

    override fun initData(savedInstanceState: Bundle?) {
        mList = ArrayList()

        enhancedRecyclerView.setOnRefreshListener(this)
        enhancedRecyclerView.setLayoutManager(getLayoutManager())
        enhancedRecyclerView.setAdapter(setupAdapter())
        enhancedRecyclerView.addItemDecoration(getItemDecoration())

        (enhancedRecyclerView.recyclerView
                .itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    /**
     * fragment 动画结束后调用 PullRecycler 刷新数据
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        enhancedRecyclerView.setRefreshing()
    }

    protected abstract fun setupAdapter(): GeneralRecyclerViewAdapter

    protected open fun getLayoutManager(): ILayoutManager {
        return BaseLinearLayoutManager(context)
    }

    protected open fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return DividerItemDecoration(context, R.drawable.list_divider)
    }

}