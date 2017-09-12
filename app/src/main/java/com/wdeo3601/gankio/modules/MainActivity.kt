package com.wdeo3601.gankio.modules

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.base.activity.BaseActicity
import com.wdeo3601.gankio.modules.delayupdate.DelayUpdateFragment

class MainActivity : BaseActicity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        ButterKnife.bind(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadRootFragment(R.id.container_view,DelayUpdateFragment())
    }

}
