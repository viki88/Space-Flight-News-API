package com.vikination.spaceflightnewsapp.data.models

abstract class BaseResponse{
    val count :Int? = null
    val next :String? = null
    val previous :String? = null
    var type :NewsType = NewsType.ARTICLE
}

enum class NewsType{
    ARTICLE,
    BLOG,
    REPORT
}