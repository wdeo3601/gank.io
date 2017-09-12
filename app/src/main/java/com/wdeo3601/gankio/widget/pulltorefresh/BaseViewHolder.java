package com.wdeo3601.gankio.widget.pulltorefresh;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected final SparseArray<View> views;
    protected View convertView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        convertView = itemView;
        this.views = new SparseArray<>();
    }

    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
