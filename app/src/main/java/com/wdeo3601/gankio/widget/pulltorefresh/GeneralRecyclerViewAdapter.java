package com.wdeo3601.gankio.widget.pulltorefresh;

import android.support.annotation.Nullable;
import android.view.View;


import com.wdeo3601.gankio.widget.pulltorefresh.multitype.MultiTypeAdapter;
import com.wdeo3601.gankio.widget.pulltorefresh.multitype.MultiTypePool;

import java.util.List;

/**
 * Created by Meng on 2017/2/17.
 */

public class GeneralRecyclerViewAdapter extends MultiTypeAdapter {
    public interface OnItemClickListener {
        void onClick(int position, View itemView);

        void onLongClick(int position, View itemView);
    }

    //region Public Methods
    public GeneralRecyclerViewAdapter() {
        super(null);
    }


    public GeneralRecyclerViewAdapter(@Nullable List<?> items) {
        super(items, new MultiTypePool());
    }

    public void onLoadMoreStateChanged(boolean show) {
        if (show) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }
    //endregion
}
