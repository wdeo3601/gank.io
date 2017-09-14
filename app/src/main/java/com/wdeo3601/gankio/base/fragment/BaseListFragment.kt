package com.wdeo3601.gankio.base.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View


import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.widget.pulltorefresh.EnhancedRecyclerView
import com.wdeo3601.gankio.widget.pulltorefresh.GeneralRecyclerViewAdapter
import com.wdeo3601.gankio.widget.pulltorefresh.decoration.DividerItemDecoration
import com.wdeo3601.gankio.widget.pulltorefresh.layoutManager.BaseLinearLayoutManager
import com.wdeo3601.gankio.widget.pulltorefresh.layoutManager.ILayoutManager

import java.util.ArrayList


abstract class BaseListFragment : BaseLazyMainFragment(), EnhancedRecyclerView.OnRecyclerRefreshListener {

    protected var mList: MutableList<Any>? = null
    protected lateinit var mRecyclerView: RecyclerView

    protected lateinit var enhancedRecyclerView: EnhancedRecyclerView
    protected var recyclerViewType = DEFAULT_RECYCLER_VIEW

    override val layoutId: Int
        get() = R.layout.fragment_base_list

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun initView(rootView: View?) {
        enhancedRecyclerView = rootView!!.findViewById<View>(R.id.enhancedRecyclerView) as EnhancedRecyclerView
        mRecyclerView = enhancedRecyclerView.recyclerView
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (mList == null) mList = ArrayList()


        enhancedRecyclerView.setOnRefreshListener(this)
        enhancedRecyclerView.setLayoutManager(layoutManager)
        enhancedRecyclerView.setAdapter(setupAdapter())
        enhancedRecyclerView.addItemDecoration(itemDecoration)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        enhancedRecyclerView.setRefreshing()
    }

    protected fun setupAdapter(): GeneralRecyclerViewAdapter {
        return GeneralRecyclerViewAdapter()
    }

    protected abstract fun setUpAdapter(): GeneralRecyclerViewAdapter

    protected val layoutManager: ILayoutManager
        get() = BaseLinearLayoutManager(context)

    protected val itemDecoration: RecyclerView.ItemDecoration
        get() = DividerItemDecoration(context, R.drawable.list_divider)

    companion object {
        private val SAVED_RECYCLERVIEW_TYPE = "recyclerViewType"

        protected val ENHANCED_RECYCLER_VIEW = 1
        protected val DEFAULT_RECYCLER_VIEW = 2
    }
}
