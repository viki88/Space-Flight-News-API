package com.vikination.spaceflightnewsapp.domain.repositories

import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import kotlinx.coroutines.flow.Flow

interface SpaceFlightNewsRepository{
    fun getArticles(): Flow<RequestResponse>
    fun getBlogs(): Flow<RequestResponse>
    fun getReports(): Flow<RequestResponse>
}