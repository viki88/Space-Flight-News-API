package com.vikination.spaceflightnewsapp.data.models

data class NewsResponse(
    val results :List<News>
) :BaseResponse()