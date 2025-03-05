package com.vikination.spaceflightnewsapp.domain.repositories

import com.vikination.spaceflightnewsapp.data.models.NewsType
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import kotlinx.coroutines.flow.Flow

interface SpaceFlightNewsRepository{
    fun getNewsSites() : Flow<RequestResponse>
    fun getNews(query: String? = null, newsSite :String? = null, ordering: String? = null, type :NewsType) :Flow<RequestResponse>
}