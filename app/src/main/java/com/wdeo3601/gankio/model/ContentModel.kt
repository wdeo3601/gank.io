package com.wdeo3601.gankio.model

/**
 * Created by wendong on 2017/9/14 0014.
 * Email:       wdeo3601@163.com
 * Description:
 */

class ContentModel {


    /**
     * _id : 583e3d7d421aa939befafada
     * createdAt : 2016-11-30T10:46:21.786Z
     * desc : 守望先锋的加载效果，游戏玩家们，是不是感到很亲切
     * images : ["http://img.gank.io/276d5cf0-1ff5-4a19-b555-18bcacb94f0c"]
     * publishedAt : 2016-11-30T11:35:55.916Z
     * source : chrome
     * type : Android
     * url : https://github.com/zhangyuChen1991/OverWatchLoading
     * used : true
     * who : 嗲马甲
     */

    var _id: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: String? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var isUsed: Boolean = false
    var who: String? = null
    var images: List<String>? = null
}
