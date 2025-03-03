package com.vikination.spaceflightnewsapp.data.repositories

import com.vikination.spaceflightnewsapp.data.models.NewsType
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import com.vikination.spaceflightnewsapp.data.network.SpaceFlightNewsApiService
import com.vikination.spaceflightnewsapp.domain.repositories.SpaceFlightNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SpaceFlightNewsRepositoryImpl @Inject constructor(
    private val spaceFlightNewsApiService: SpaceFlightNewsApiService
) :SpaceFlightNewsRepository {
    override fun getArticles(query: String?): Flow<RequestResponse> = flow {
        try {
            emit(RequestResponse.Loading)
            val response = spaceFlightNewsApiService.getArticles(query = query)
            response.type = NewsType.ARTICLE
            emit(RequestResponse.Success(response))
        }catch (e :Exception){
            emit(RequestResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getBlogs(query: String?): Flow<RequestResponse> = flow {
        try {
            emit(RequestResponse.Loading)
            val response = spaceFlightNewsApiService.getBlogs(query = query)
            response.type = NewsType.BLOG
            emit(RequestResponse.Success(response))
        }catch (e :Exception){
            emit(RequestResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getReports(query: String?): Flow<RequestResponse> = flow {
        try {
            emit(RequestResponse.Loading)
            val response = spaceFlightNewsApiService.getReports(query = query)
            response.type = NewsType.REPORT
            emit(RequestResponse.Success(response))
        }catch (e :Exception){
            emit(RequestResponse.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}