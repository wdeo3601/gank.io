package com.wdeo3601.gankio.modules.delayupdate.viewprovider

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.wdeo3601.gankio.R

import butterknife.BindView
import butterknife.ButterKnife
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by wendong on 2017/9/14 0014.
 * Email:       wdeo3601@163.com
 * Description:
 */

class TitleViewBinder : ItemViewBinder<String, TitleViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {

        return ViewHolder(inflater.inflate(R.layout.item_title, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: String) {
        holder.title.text = item
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)

    }
}
