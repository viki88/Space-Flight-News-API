package com.vikination.spaceflightnewsapp.data.network

import com.vikination.spaceflightnewsapp.data.models.NewsResponse
import com.vikination.spaceflightnewsapp.data.models.NewsSiteListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceFlightNewsApiService {

    @GET("v4/articles")
    suspend fun getArticles(
        @Query("format") format: String = "json",
        @Query("search") query: String? = null,
        @Query("has_launch") hasLaunch: Boolean = true,
        @Query("has_event") hasEvent: Boolean = true,
        @Query("news_site") newsSite: String? = null,
        @Query("ordering") ordering: String? = null
    ): NewsResponse

    @GET("v4/blogs")
    suspend fun getBlogs(
        @Query("format") format: String = "json",
        @Query("search") query: String? = null,
        @Query("news_site") newsSite: String? = null,
        @Query("ordering") ordering: String? = null
    ): NewsResponse

    @GET("v4/reports")
    suspend fun getReports(
        @Query("format") format: String = "json",
        @Query("search") query: String? = null,
        @Query("news_site") newsSite: String? = null,
        @Query("ordering") ordering: String? = null
    ): NewsResponse

    @GET("v4/info")
    suspend fun getInfo() : NewsSiteListResponse

}