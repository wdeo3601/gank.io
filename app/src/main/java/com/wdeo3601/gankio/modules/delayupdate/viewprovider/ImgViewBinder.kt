package com.wdeo3601.gankio.modules.delayupdate.viewprovider

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.wdeo3601.gankio.R
import com.wdeo3601.gankio.model.ContentModel
import com.wdeo3601.gankio.utils.ImageLoader
import me.drakeet.multitype.ItemViewBinder

/**
 * Created by wendong on 2017/9/14 0014.
 * Email:       wdeo3601@163.com
 * Description:
 */

class ImgViewBinder : ItemViewBinder<ContentModel, ImgViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {

        return ViewHolder(inflater.inflate(R.layout.item_img, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ContentModel) {
        ImageLoader.instance.loadImageUrlCenter(holder.itemView.context, item.url!!, holder.img, null)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)
    }
}