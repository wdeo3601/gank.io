package com.wdeo3601.gankio.widget.pulltorefresh.multitype;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Meng on 2017/2/19.
 */

public interface TypePool {

    void register(@NonNull Class<?> clazz, @NonNull ItemViewProvider provider);

    /**
     * For getting index of the item class.
     * If the subclass is already registered, the registered mapping is used.
     * If the subclass is not registered, then look for the parent class is
     * registered, if the parent class is registered,
     * the subclass is regarded as the parent class.
     *
     * @param clazz the item class.
     * @return the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    int indexOf(@NonNull Class<?> clazz);

    @NonNull
    ArrayList<Class<?>> getContents();

    @NonNull
    ArrayList<ItemViewProvider> getProviders();

    @NonNull
    ItemViewProvider getProviderByIndex(int index);

    @NonNull
    <T extends ItemViewProvider> T getProviderByClass(@NonNull Class<?> clazz);
}
