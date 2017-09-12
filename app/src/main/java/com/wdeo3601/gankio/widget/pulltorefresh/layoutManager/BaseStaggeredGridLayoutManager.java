package com.wdeo3601.gankio.widget.pulltorefresh.layoutManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.wdeo3601.gankio.widget.pulltorefresh.GeneralRecyclerViewAdapter;

public class BaseStaggeredGridLayoutManager extends StaggeredGridLayoutManager
        implements ILayoutManager {

    public BaseStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                          int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BaseStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int findLastVisiblePosition() {
        int[] positions = null;
        positions = findLastVisibleItemPositions(positions);
        return positions[0];
    }

    @Override
    public int findFirstCompletelyVisibleItemPosition() {
        return 0;
    }

    @Override
    public int findFirstVisibleItemPosition() {
        return 0;
    }

    @Override
    public int findLastVisibleItemPosition() {
        return 0;
    }

    @Override
    public void setUpAdapter(GeneralRecyclerViewAdapter adapter) {

    }
}
