package com.wdeo3601.gankio.widget.pulltorefresh.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Meng on 2017/2/19.
 */

public abstract class ItemViewProvider<T, V extends RecyclerView.ViewHolder> {
    public RecyclerView.Adapter adapter;


    @NonNull
    protected abstract V onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull V holder, @NonNull T t);

    protected void onBindViewHolder(@NonNull V holder, @NonNull T t, List<Object> payloads){
        onBindViewHolder(holder,t);
    }


    /**
     * Get the RecyclerView.Adapter for sending notifications or getting item count, etc.
     *
     * @return The RecyclerView.Adapter this item is currently associated with.
     * @since v2.3.4
     */
    @NonNull
    protected final RecyclerView.Adapter getAdapter() {
        return adapter;
    }
}
