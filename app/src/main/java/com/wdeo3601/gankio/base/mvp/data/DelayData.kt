package com.wdeo3601.gankio.base.mvp.data

/**
 * Created by wendong on 2017/9/12 0012.
 * Email:       wdeo3601@163.com
 * Description:
 */
class DelayData<T>(var category: List<String>, results: Results<T>, error: Boolean) : BaseData<T>(results, error)