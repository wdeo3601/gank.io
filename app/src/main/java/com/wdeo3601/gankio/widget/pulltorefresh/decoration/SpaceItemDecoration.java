package com.wdeo3601.gankio.widget.pulltorefresh.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wdeo3601.gankio.utils.UIUtil;


/**
 * Created by lwj on 7/20/2016.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontalSpacing, mVerticalSpacing;

    public SpaceItemDecoration(int spacing) {
        mHorizontalSpacing = spacing;
        mVerticalSpacing = spacing;
    }

    public SpaceItemDecoration(int mHorizontalSpacing, int mVerticalSpacing) {
        this.mHorizontalSpacing = mHorizontalSpacing;
        this.mVerticalSpacing = mVerticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        outRect.top = 0;
        outRect.left = 0;
        outRect.bottom = mVerticalSpacing;
        outRect.right = mHorizontalSpacing;
        if (position == 0 || position == 1 || position == 2) {
            outRect.top = UIUtil.INSTANCE.dp2px(15);
        }
    }
}