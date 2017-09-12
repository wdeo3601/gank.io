package com.wdeo3601.gankio.widget.pulltorefresh.multitype;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meng on 2017/2/19.
 */

public abstract class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FlatTypeAdapter, TypePool {
    @Nullable
    protected List<?> items;
    @NonNull
    protected TypePool delegate;
    @Nullable
    private LayoutInflater inflater;
    @Nullable
    private FlatTypeAdapter providedFlatTypeAdapter;

    //region Public Methods
    public MultiTypeAdapter() {
        this(null);
    }


    public MultiTypeAdapter(@Nullable List<?> items) {
        this(items, new MultiTypePool());
    }


    public MultiTypeAdapter(@Nullable List<?> items, int initialCapacity) {
        this(items, new MultiTypePool(initialCapacity));
    }


    public MultiTypeAdapter(
            @Nullable List<?> items, @NonNull TypePool delegate) {
        this.items = items;
        this.delegate = delegate;
    }

    public void registerAll(@NonNull final MultiTypePool pool) {
        for (int i = 0; i < pool.getContents().size(); i++) {
            delegate.register(pool.getContents().get(i), pool.getProviders().get(i));
        }
    }
    //endregion

    /**
     * Update the items atomically and safely.
     * It is recommended to use this method to update the data.
     * <p>e.g. {@code adapter.setItems(new Items(changedItems));}</p>
     * <p>
     * <p>Note: If you want to refresh the list views, you should
     * call {@link RecyclerView.Adapter#notifyDataSetChanged()} by yourself.</p>
     *
     * @param items The <b>new</b> items list.
     * @since v2.4.1
     */
    public void setItems(@Nullable List<?> items) {
        this.items = items;
    }

    /**
     * Set the FlatTypeAdapter to instead of the default inner FlatTypeAdapter of
     * MultiTypeAdapter.
     * <p>Note: You could use {@link FlatTypeClassAdapter} and {@link FlatTypeItemAdapter}
     * to create a special FlatTypeAdapter conveniently.</p>
     *
     * @param flatTypeAdapter the FlatTypeAdapter
     * @since v2.3.2
     */
    public void setFlatTypeAdapter(@NonNull FlatTypeAdapter flatTypeAdapter) {
        this.providedFlatTypeAdapter = flatTypeAdapter;
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        return indexOf(flattenClass(item));
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemViewProvider provider = getProviderByIndex(viewType);
        provider.adapter = MultiTypeAdapter.this;
        return provider.onCreateViewHolder(inflater, parent);
    }


    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Object item = items.get(position);
//        ItemViewProvider provider = getProviderByClass(item.getClass());
//        provider.onBindViewHolder(holder, item);
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        Object item = items.get(position);
        ItemViewProvider provider = getProviderByClass(flattenClass(item));
        provider.onBindViewHolder(holder, item, payloads);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    //region TypePool Implementation
    @Override
    public void register(@NonNull Class<?> clazz, @NonNull ItemViewProvider provider) {
        delegate.register(clazz, provider);
    }


    @Override
    public int indexOf(@NonNull Class<?> clazz)
            throws ProviderNotFoundException {
        int index = delegate.indexOf(clazz);
        if (index >= 0) {
            return index;
        }
        throw new ProviderNotFoundException(clazz);
    }

    @NonNull
    @SuppressWarnings("deprecation")
    final Class<?> flattenClass(@NonNull final Object item) {
        if (providedFlatTypeAdapter != null) {
            return providedFlatTypeAdapter.onFlattenClass(item);
        }
        return onFlattenClass(item);
    }


    @NonNull
    @SuppressWarnings("deprecation")
    final Object flattenItem(@NonNull final Object item) {
        if (providedFlatTypeAdapter != null) {
            return providedFlatTypeAdapter.onFlattenItem(item);
        }
        return onFlattenItem(item);
    }


    /**
     * @deprecated Use {@link MultiTypeAdapter#setFlatTypeAdapter(FlatTypeAdapter)} instead.
     * The method may be removed next time.
     */
    @NonNull
    @Override
    public Class<?> onFlattenClass(@NonNull final Object item) {
        return item.getClass();
    }


    /**
     * @deprecated Use {@link MultiTypeAdapter#setFlatTypeAdapter(FlatTypeAdapter)} instead.
     * The method may be removed next time.
     */
    @NonNull
    @Override
    public Object onFlattenItem(@NonNull final Object item) {
        return item;
    }

    @NonNull
    @Override
    public ArrayList<Class<?>> getContents() {
        return delegate.getContents();
    }


    @NonNull
    @Override
    public ArrayList<ItemViewProvider> getProviders() {
        return delegate.getProviders();
    }


    @NonNull
    @Override
    public ItemViewProvider getProviderByIndex(int index) {
        return delegate.getProviderByIndex(index);
    }


    @NonNull
    @Override
    public <ProviderT extends ItemViewProvider> ProviderT getProviderByClass(@NonNull Class<?> clazz) {
        return delegate.getProviderByClass(clazz);
    }
    //endregion
}
