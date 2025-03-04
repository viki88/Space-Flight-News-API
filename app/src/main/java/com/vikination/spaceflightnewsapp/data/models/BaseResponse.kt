package com.vikination.spaceflightnewsapp.data.models

abstract class BaseResponse{
    val count :Int? = null
    val next :String? = null
    val previous :String? = null
    var type :NewsType = NewsType.ARTICLE
}

sealed class NewsType(val value :String){
    data object ARTICLE : NewsType("Article")
    data object BLOG :NewsType("Blog")
    data object REPORT :NewsType("Report")
}