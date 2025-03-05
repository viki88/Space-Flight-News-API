package com.vikination.spaceflightnewsapp.data.models

import com.vikination.spaceflightnewsapp.domain.models.News

data class NewsData(
    var newsType: NewsType,
    var results: List<News> = emptyList()
)