package com.wdeo3601.gankio.widget.pulltorefresh;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
import me.drakeet.multitype.MultiTypePool;

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
