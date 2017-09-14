package com.wdeo3601.gankio.modules.delayupdate.viewprovider

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.model.ContentModel
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by wendong on 2017/9/14 0014.
 * Email:       wdeo3601@163.com
 * Description:
 */

class ArticleViewBinder : ItemViewBinder<ContentModel, ArticleViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {

        return ViewHolder(inflater.inflate(R.layout.item_article, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, item: ContentModel) {
        holder.textviewWho.text = item.who
        var publishedAt = item.publishedAt
        publishedAt = publishedAt!!.substring(0, publishedAt.lastIndexOf("T"))
        holder.textviewPublishTime.text = "推送日期：" + publishedAt
        holder.content.text = item.desc
        holder.textviewCreateTime.text = item.createdAt
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textviewWho: TextView = itemView.findViewById(R.id.textview_who)
        var textviewPublishTime: TextView = itemView.findViewById(R.id.textview_publish_time)
        var content: TextView = itemView.findViewById(R.id.textview_desc)
        var textviewCreateTime: TextView = itemView.findViewById(R.id.textview_create_time)
    }
}