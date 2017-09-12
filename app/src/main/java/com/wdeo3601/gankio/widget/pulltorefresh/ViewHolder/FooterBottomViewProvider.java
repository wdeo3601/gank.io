package com.wdeo3601.gankio.widget.pulltorefresh.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdeo3601.gankio.R;
import com.wdeo3601.gankio.widget.pulltorefresh.multitype.ItemViewProvider;

/**
 * Created by Meng on 2017/2/19.
 */

public class FooterBottomViewProvider extends ItemViewProvider<Object, FooterBottomViewProvider.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.widget_pull_to_refresh_bottom, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Object o) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
