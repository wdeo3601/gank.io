package com.wdeo3601.gankio.widget.pulltorefresh.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wdeo3601.gankio.utils.UIUtil;


/**
 * Created by lwj on 7/20/2016.
 */
public class GiftShopSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontalSpacing, mVerticalSpacing;

    public GiftShopSpaceItemDecoration(int spacing) {
        mHorizontalSpacing = spacing;
        mVerticalSpacing = spacing;
    }

    public GiftShopSpaceItemDecoration(int mHorizontalSpacing, int mVerticalSpacing) {
        this.mHorizontalSpacing = mHorizontalSpacing;
        this.mVerticalSpacing = mVerticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        outRect.bottom = mVerticalSpacing;
        if (position % 4 == 1) {
            outRect.left = mHorizontalSpacing / 5 * 4;
            outRect.right = mHorizontalSpacing / 5;
        } else if (position % 4 == 2) {
            outRect.left = mHorizontalSpacing / 5 * 3;
            outRect.right = mHorizontalSpacing / 5 * 2;
        } else if (position % 4 == 3) {
            outRect.left = mHorizontalSpacing / 5 * 2;
            outRect.right = mHorizontalSpacing / 5 * 3;
        } else if (position % 4 == 0 && position != 0) {
            outRect.left = mHorizontalSpacing / 5;
            outRect.right = mHorizontalSpacing / 5 * 4;
        }

        if (position == 0) {
            outRect.top = UIUtil.INSTANCE.dp2px(15);
            outRect.left = mHorizontalSpacing / 5 * 2;
        } else {
            outRect.top = 0;
        }

    }
}