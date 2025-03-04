package com.vikination.spaceflightnewsapp.data.mapper

import com.vikination.spaceflightnewsapp.data.models.NewsResponseModel
import com.vikination.spaceflightnewsapp.data.utils.DateFormatter
import com.vikination.spaceflightnewsapp.domain.models.News

object NewsMapper {

    fun mapToNewsUiModel(newsResponseModel: NewsResponseModel): News {
        return News(
            id = newsResponseModel.id ?: 0,
            title = newsResponseModel.title ?: "",
            authors = newsResponseModel.authors ?: emptyList(),
            imageUrl = newsResponseModel.imageUrl ?: "",
            summary = newsResponseModel.summary ?: "",
            url = newsResponseModel.url ?: "",
            newsSite = newsResponseModel.newsSite ?: "",
            publishedAt = DateFormatter.formatDate(newsResponseModel.publishedAt),
            updatedAt = newsResponseModel.updatedAt ?: "",
            featured = newsResponseModel.featured ?: false,
            launches = newsResponseModel.launches ?: emptyList(),
            events = newsResponseModel.events ?: emptyList()
        )
    }

    fun mapToNewsUiModelList(newsResponseModels: List<NewsResponseModel>): List<News> {
        return newsResponseModels.map { mapToNewsUiModel(it) }
    }
}