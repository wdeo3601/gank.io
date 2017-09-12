package com.wdeo3601.gankio.modules.delayupdate

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.orhanobut.logger.Logger
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.base.fragment.BaseErrorListFragment
import com.wdeo3601.gankio.base.mvp.data.DelayData
import com.wdeo3601.gankio.network.HttpSubscriber
import com.wdeo3601.gankio.widget.pulltorefresh.GeneralRecyclerViewAdapter
import rx.android.schedulers.AndroidSchedulers

@Suppress("UNREACHABLE_CODE")
/**
 * Created by wendong on 2017/9/12 0012.
 * Email:       wdeo3601@163.com
 * Description:每日更新
 */
class DelayUpdateFragment : BaseErrorListFragment() {
    var textview: TextView? = null

    companion object {
        var instance = DelayUpdateFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_delay_update

    override fun initView(rootView: View?) {
        super.initView(rootView)
        textview = rootView!!.findViewById<TextView>(R.id.response)
        showError(false)
    }

    override fun retryConnectToInternet() {
        enhancedRecyclerView.setRefreshing()
    }

    override fun setUpAdapter(): GeneralRecyclerViewAdapter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRefresh(action: Int) {
        addSubscription(baseApi().getDelayData(2017,9,5)
                .subscribeOn(rx.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :HttpSubscriber<DelayData<String>>() {
                    override fun onSuccess(model: DelayData<String>?) {
                        Logger.e("json",model?.results)
                        textview?.text =model?.results
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }


                }))
    }

}