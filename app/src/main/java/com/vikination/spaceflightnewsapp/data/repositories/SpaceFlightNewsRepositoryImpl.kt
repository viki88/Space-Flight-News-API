package com.vikination.spaceflightnewsapp.data.repositories

import com.vikination.spaceflightnewsapp.data.mapper.NewsEntityMapper.toNewsUiModel
import com.vikination.spaceflightnewsapp.data.mapper.NewsResponseMapper.toNewsEntities
import com.vikination.spaceflightnewsapp.data.models.NewsData
import com.vikination.spaceflightnewsapp.data.models.NewsType
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import com.vikination.spaceflightnewsapp.data.source.local.dao.NewsDao
import com.vikination.spaceflightnewsapp.data.source.remote.SpaceFlightNewsApiService
import com.vikination.spaceflightnewsapp.domain.repositories.SpaceFlightNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SpaceFlightNewsRepositoryImpl @Inject constructor(
    private val spaceFlightNewsApiService: SpaceFlightNewsApiService,
    private val newsDao: NewsDao
) :SpaceFlightNewsRepository {

    override fun getNewsSites(): Flow<RequestResponse> = flow {
        try {
            emit(RequestResponse.Loading)
            val response = spaceFlightNewsApiService.getInfo()
            emit(RequestResponse.Success(response))
        }catch (e :Exception){
            emit(RequestResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getNews(
        query: String?,
        newsSite: String?,
        ordering: String?,
        type: NewsType
    ): Flow<RequestResponse> = flow {
        try {
            emit(RequestResponse.Loading)

            // Get data from database
            val newsData = NewsData(
                newsType = type
            )
            newsDao.getAllNews(type = type.value).collect{
                newsData.results = it.toNewsUiModel()
                emit(RequestResponse.Success(newsData))
            }

            // Get data from API
            val response =
                when(type){
                    NewsType.ARTICLE -> spaceFlightNewsApiService.getArticles(
                        query = query,
                        newsSite = newsSite,
                        ordering = ordering
                    )
                    NewsType.BLOG -> spaceFlightNewsApiService.getBlogs(
                        query = query,
                        newsSite = newsSite,
                        ordering = ordering
                    )
                    NewsType.REPORT -> spaceFlightNewsApiService.getReports(
                        query = query,
                        newsSite = newsSite,
                        ordering = ordering
                    )
                }

            // Delete old data and insert new data
            newsDao.deleteAllNews(type.value)
            newsDao.insertNews(response.results.toNewsEntities(type.value))

        }catch (e :Exception){
            emit(RequestResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

}