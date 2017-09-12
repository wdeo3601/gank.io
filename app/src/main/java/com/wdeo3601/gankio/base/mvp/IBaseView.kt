package com.wdeo3601.gankio.base.mvp

import android.content.Context

import me.yokeyword.fragmentation.SupportFragment
import rx.Subscription

/**
 * Created by lwj on 8/11/2016.
 */
interface IBaseView {
    fun setLoadingView(show: Boolean)

    fun addSubscription(subscription: Subscription)

    fun startFragment(fragment: SupportFragment)

    fun showDialog(s: String)

    val viewContext: Context
}
