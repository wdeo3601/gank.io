package com.wdeo3601.gankio.widget.pulltorefresh.layoutManager;

import android.support.v7.widget.RecyclerView;

import com.wdeo3601.gankio.widget.pulltorefresh.GeneralRecyclerViewAdapter;

/**
 * LayoutManager 的接口类
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();

    int findLastVisiblePosition();

    int findFirstCompletelyVisibleItemPosition();

    int findFirstVisibleItemPosition();

    int findLastVisibleItemPosition();

    void setUpAdapter(GeneralRecyclerViewAdapter adapter);
}
