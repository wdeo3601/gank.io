package com.wdeo3601.gankio.constant


object Constants {

    //用于系统被强杀等的的标记
    val STATUS_FORCE_KILLED = -1
    val STATUS_LOGOUT = 0
    val STATUS_OFFLINE = 1
    val STATUS_ONLINE = 2
    val STATUS_KICK_OUT = 3
    val KEY_HOME_ACTION = "key_home_action"
    val ACTION_BACK_TO_HOME = 0
    val ACTION_RESTART_APP = 1
    val ACTION_LOGOUT = 2
    val ACTION_KICK_OUT = 3

    //api版本控制
    val ACCEPT = "Accept:application/json; version="//接口的版本控制

    // Base API
    val BASE_URL = "http://gank.io/api/"

    //contentType
    val TYPE_ANDROID = "Android"
    val TYPE_WELFARE = "福利"
    val TYPE_IOS = "iOS"
    val TYPE_REST_VIDEO = "休息视频"
    val TYPE_EXPANDING_RESOURCE = "拓展资源"
    val TYPE_WEB = "前端"


}
