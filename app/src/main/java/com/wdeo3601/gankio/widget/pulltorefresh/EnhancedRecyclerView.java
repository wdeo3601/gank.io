package com.wdeo3601.gankio.widget.pulltorefresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.wdeo3601.gankio.App;
import com.wdeo3601.gankio.R;
import com.wdeo3601.gankio.utils.ImageLoader;
import com.wdeo3601.gankio.utils.UIUtil;
import com.wdeo3601.gankio.widget.pulltorefresh.layoutManager.ILayoutManager;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;


public class EnhancedRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    public static final int ACTION_IDLE = 0;
    public int mCurrentState = ACTION_IDLE;
    public ILayoutManager mLayoutManager;
    public boolean isTop = true; // 是否在顶部
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private OnRecyclerRefreshListener listener;
    private OnScrolledListener mScrolledListener;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private GeneralRecyclerViewAdapter adapter;
    private int mScrollTotal;
    private int mIndex;
    private boolean move;

    public EnhancedRecyclerView(Context context) {
        super(context);
        setupView();
    }

    public EnhancedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public EnhancedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void setupView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_pull_to_refresh_recycler_view, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    move = false;
                    int n = mIndex - mLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRecyclerView.getChildCount()) {
                        int top = mRecyclerView.getChildAt(n).getTop();
                        mRecyclerView.smoothScrollBy(0, top);
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (mScrolledListener != null) {
                        mScrolledListener.onScrolled(false);
                    }
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ImageLoader.Companion.getInstance().resume(App.instance.mContext);
                    int lastVisibleItemPosition = mLayoutManager.findLastVisiblePosition();
                    int totalCount = mLayoutManager.getLayoutManager().getItemCount();
                    if ((totalCount - lastVisibleItemPosition) == 1) {
                        // 到了页面底部
                        if (mScrolledListener != null) {
                            mScrolledListener.onScrolled(true);
                        }
                    }
                } else {
                    ImageLoader.Companion.getInstance().pause(App.instance.mContext);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在这里进行第二次滚动（最后的100米！）
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - mLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRecyclerView.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = mRecyclerView.getChildAt(n).getTop();
                        //最后的移动
                        mRecyclerView.scrollBy(0, top);
                    }
                }
                //                mSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                if (mCurrentState == ACTION_IDLE && isLoadMoreEnabled && checkIfLoadMore()) {
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;
                    UIUtil.INSTANCE.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.onLoadMoreStateChanged(true);
                            mSwipeRefreshLayout.setEnabled(false);
                            listener.onRefresh(ACTION_LOAD_MORE_REFRESH);
                        }
                    });
                }
                mScrollTotal += dy;
                isTop = mScrollTotal <= 0;
            }
        });
    }

    private boolean checkIfLoadMore() {
        int lastVisibleItemPosition = mLayoutManager.findLastVisiblePosition();
        int totalCount = mLayoutManager.getLayoutManager().getItemCount();
        return totalCount - lastVisibleItemPosition < 5;
    }

    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public void setLayoutManager(ILayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager.getLayoutManager());
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        if (decoration != null) {
            mRecyclerView.addItemDecoration(decoration);
        }
    }

    public void setAdapter(GeneralRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
//        mLayoutManager.setUpAdapter(adapter);
    }

    public void setRefreshing() {
        setSwipeRefreshing(true);
        onRefresh();
    }

    public void setSwipeRefreshing(final boolean isRefreshing) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    public void setOnRefreshListener(OnRecyclerRefreshListener listener) {
        this.listener = listener;
    }

    public void setOnScrolledListener(OnScrolledListener listener) {
        this.mScrolledListener = listener;
    }

    @Override
    public void onRefresh() {
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public void onRefreshCompleted() {
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                adapter.onLoadMoreStateChanged(false);
                if (isPullToRefreshEnabled) {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                break;
            default:
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
                break;
        }
        mCurrentState = ACTION_IDLE;
    }

    /**
     * 滑动到顶部
     */
    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    public void scrollToPositionOffset(int position, int offset) {
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position,
                offset);
    }

    public void scrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    public int getFirstVisibleItemPosition() {
        return mLayoutManager.findFirstVisibleItemPosition();
    }

    public int getLastVisibleItemPosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    public void moveToPosition(int n) {
        int firstItem = getFirstVisibleItemPosition();
        int lastItem = getLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
            move = true;
        }
    }

    public void smoothMoveToPosition(int n) {

        int firstItem = getFirstVisibleItemPosition();
        int lastItem = getLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(n);
            move = true;
        }

    }

    public void onDestroy() {
        mRecyclerView.setAdapter(null);
    }

    public interface OnRecyclerRefreshListener {
        void onRefresh(int action);
    }

    public interface OnScrolledListener {
        /**
         * @param scrolledToBottom 是否滚动到了页面底部
         */
        void onScrolled(boolean scrolledToBottom);
    }
}
