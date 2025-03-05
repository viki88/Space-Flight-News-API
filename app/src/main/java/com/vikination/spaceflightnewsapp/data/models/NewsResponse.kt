package com.vikination.spaceflightnewsapp.data.models

data class NewsResponse(
    var results :List<NewsResponseModel>
) :BaseResponse()