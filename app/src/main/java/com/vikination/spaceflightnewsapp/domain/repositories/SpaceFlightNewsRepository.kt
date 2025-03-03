package com.vikination.spaceflightnewsapp.domain.repositories

import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import kotlinx.coroutines.flow.Flow

interface SpaceFlightNewsRepository{
    fun getArticles(query: String? = null): Flow<RequestResponse>
    fun getBlogs(query: String? = null): Flow<RequestResponse>
    fun getReports(query: String? = null): Flow<RequestResponse>
}