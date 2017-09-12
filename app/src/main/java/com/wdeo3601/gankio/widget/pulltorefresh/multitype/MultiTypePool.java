package com.wdeo3601.gankio.widget.pulltorefresh.multitype;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Meng on 2017/2/19.
 */

public final class MultiTypePool implements TypePool {
    private final String TAG = MultiTypePool.class.getSimpleName();
    private ArrayList<Class<?>> contents;
    private ArrayList<ItemViewProvider> providers;


    public MultiTypePool() {
        this.contents = new ArrayList<>();
        this.providers = new ArrayList<>();
    }


    public MultiTypePool(int initialCapacity) {
        this.contents = new ArrayList<>(initialCapacity);
        this.providers = new ArrayList<>(initialCapacity);
    }


    public void register(@NonNull Class<?> clazz, @NonNull ItemViewProvider provider) {
        if (!contents.contains(clazz)) {
            contents.add(clazz);
            providers.add(provider);
        } else {
            int index = contents.indexOf(clazz);
            providers.set(index, provider);
            Log.w(TAG, "You have registered the " + clazz.getSimpleName() + " type. " +
                    "It will override the original provider.");
        }
    }


    public int indexOf(@NonNull final Class<?> clazz) {
        int index = contents.indexOf(clazz);
        if (index >= 0) {
            return index;
        }
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return index;
    }


    @NonNull
    public ArrayList<Class<?>> getContents() {
        return contents;
    }


    @NonNull
    public ArrayList<ItemViewProvider> getProviders() {
        return providers;
    }


    @NonNull
    public ItemViewProvider getProviderByIndex(int index) {
        return providers.get(index);
    }


    @SuppressWarnings("unchecked")
    @NonNull
    public <T extends ItemViewProvider> T getProviderByClass(@NonNull final Class<?> clazz) {
        return (T) getProviderByIndex(indexOf(clazz));
    }
}
