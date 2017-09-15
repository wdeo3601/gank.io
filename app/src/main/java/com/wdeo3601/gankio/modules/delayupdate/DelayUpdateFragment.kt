package com.wdeo3601.gankio.modules.delayupdate

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton
import com.nightonke.boommenu.BoomMenuButton
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.base.fragment.BaseToolbarListFragment
import com.wdeo3601.gankio.constant.Constants
import com.wdeo3601.gankio.model.ContentModel
import com.wdeo3601.gankio.modules.delayupdate.viewprovider.ArticleViewBinder
import com.wdeo3601.gankio.modules.delayupdate.viewprovider.ImgViewBinder
import com.wdeo3601.gankio.modules.delayupdate.viewprovider.TitleViewBinder
import com.wdeo3601.gankio.network.HttpSubscriber
import com.wdeo3601.gankio.utils.UIUtil
import com.wdeo3601.gankio.widget.pulltorefresh.GeneralRecyclerViewAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


@Suppress("UNREACHABLE_CODE")
/**
 * Created by wendong on 2017/9/12 0012.
 * Email:       wdeo3601@163.com
 * Description:每日更新
 */
class DelayUpdateFragment : BaseToolbarListFragment() {

    lateinit var adapter: GeneralRecyclerViewAdapter
    lateinit var boomButton: BoomMenuButton

    var menuicons = arrayOf(R.drawable.android,R.drawable.ios,R.drawable.web,R.drawable.extra,R.drawable.img,R.drawable.video)
    var menutexts = arrayOf("Android","Ios","Web前端","拓展资源","福利","休息视频")

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance = DelayUpdateFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_delay_update

    override fun initView(rootView: View?) {
        super.initView(rootView)
        setTitle("每日更新")
        boomButton = rootView!!.findViewById(R.id.bmb)
        enhancedRecyclerView.enableLoadMore(false)
        enhancedRecyclerView.setBackgroundColor(Color.rgb(243, 244, 246))
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initBoomMenuButton()
    }

    private fun initBoomMenuButton() {
        (0 until boomButton.piecePlaceEnum.pieceNumber())
                .map {
                    TextOutsideCircleButton.Builder()
                            .listener { index -> Toast.makeText(_mActivity, "Clicked " + index, Toast.LENGTH_SHORT).show() }
                            .normalImageRes(R.drawable.default_menu)
                            .normalText(menutexts[it])
                }
                .forEach { boomButton.addBuilder(it) }
    }

    override fun onRefresh(action: Int) {
        getDelayData()
    }

    private fun getDelayData() {
        addSubscription(baseApi().updateDate
                .flatMap { t ->
                    var reccentDate = t.results.get(0)
                    reccentDate = reccentDate.replace("-", "/")
                    baseApi().getDelayData(reccentDate)
                }
                .map { t ->
                    val categoryList = t.category
                    var gson = Gson()
                    var list = mutableListOf<Any>()
                    for (cate: String in categoryList) {
                        list.add(cate)
                        val asJsonArray = t.results.getAsJsonArray(cate)
                        asJsonArray
                                .map { gson.fromJson<ContentModel>(it, ContentModel::class.java) }
                                .forEach { list.add(it) }
                    }
                    list
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpSubscriber<MutableList<Any>>() {
                    override fun onSuccess(list: MutableList<Any>) {
                        adapter.notifyItemRangeRemoved(0, mList.size)
                        mList.clear()

                        mList.addAll(list)
                        adapter.notifyItemRangeInserted(0, mList.size)
                    }

                    override fun onComplete() {
                        enhancedRecyclerView.onRefreshCompleted()
                    }

                })
        )
    }

    override fun setupAdapter(): GeneralRecyclerViewAdapter {
        adapter = GeneralRecyclerViewAdapter(mList)
        adapter.register(String::class.java, TitleViewBinder())
        adapter.register(ContentModel::class.java).to(
                ArticleViewBinder(),
                ImgViewBinder()
        ).withLinker { t ->
            val type = t.type
            when (type) {
                Constants.TYPE_WELFARE -> 1
                else -> 0
            }
        }
        return adapter
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }
}