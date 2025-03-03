package com.vikination.spaceflightnewsapp.data.network

import com.vikination.spaceflightnewsapp.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceFlightNewsApiService {

    @GET("v4/articles")
    suspend fun getArticles(
        @Query("format") format: String = "json"
    ): NewsResponse

    @GET("v4/blogs")
    suspend fun getBlogs(
        @Query("format") format: String = "json"
    ): NewsResponse

    @GET("v4/reports")
    suspend fun getReports(
        @Query("format") format: String = "json"
    ): NewsResponse

}