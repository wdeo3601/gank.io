package com.wdeo3601.gankio.widget.pulltorefresh.multitype;

/**
 * Created by Meng on 2017/2/19.
 */

public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException(Class<?> clazz) {
        super("Do you have registered the provider for {className}.class in the adapter/pool?"
                .replace("{className}", clazz.getSimpleName()));
    }
}
