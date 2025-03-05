package com.vikination.spaceflightnewsapp.data.mapper

import com.vikination.spaceflightnewsapp.data.models.AuthorResponse
import com.vikination.spaceflightnewsapp.data.models.EventResponse
import com.vikination.spaceflightnewsapp.data.models.LaunchResponse
import com.vikination.spaceflightnewsapp.data.models.NewsResponseModel
import com.vikination.spaceflightnewsapp.data.models.SocialResponse
import com.vikination.spaceflightnewsapp.data.source.local.entity.AuthorEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.EventEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.LaunchEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.NewsEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.SocialEntity

object NewsResponseMapper {

    /**
     * mapper collection for NewsEntity
     */
    fun List<NewsResponseModel>.toNewsEntities(type :String): List<NewsEntity> {
        return this.map { mapToNewsEntity(it, type) }
    }

    fun mapToNewsEntity(newsResponseModel: NewsResponseModel, type :String): NewsEntity {
        return NewsEntity(
            id = newsResponseModel.id ?: 0,
            title = newsResponseModel.title ?: "",
            authors = newsResponseModel.authorResponses?.toAuthorEntities().orEmpty(),
            imageUrl = newsResponseModel.imageUrl ?: "",
            summary = newsResponseModel.summary ?: "",
            url = newsResponseModel.url ?: "",
            newsSite = newsResponseModel.newsSite ?: "",
            publishedAt = newsResponseModel.publishedAt ?: "",
            updatedAt = newsResponseModel.updatedAt ?: "",
            featured = newsResponseModel.featured ?: false,
            launches = newsResponseModel.launchResponses?.toLaunchEntities().orEmpty(),
            events = newsResponseModel.eventResponses?.toEventEntities().orEmpty(),
            type = type
        )
    }

    fun mapToAuthorEntity(authorResponse: AuthorResponse): AuthorEntity {
        return AuthorEntity(
            name = authorResponse.name ?: "",
            socials = mapToSocialEntity(authorResponse.socials ?: emptySocialResponse())
        )
    }

    fun List<AuthorResponse>.toAuthorEntities(): List<AuthorEntity> {
        return this.map { mapToAuthorEntity(it) }
    }

    fun mapToLaunchEntity(launchResponse: LaunchResponse): LaunchEntity {
        return LaunchEntity(
            id = launchResponse.id ?: "",
            provider = launchResponse.provider ?: ""
        )
    }

    fun List<LaunchResponse>.toLaunchEntities(): List<LaunchEntity> {
        return this.map { mapToLaunchEntity(it) }
    }

    fun mapToEventEntity(eventResponse: EventResponse) : EventEntity {
        return EventEntity(
            id = eventResponse.id ?: "",
            provider = eventResponse.provider ?: ""
        )
    }

    fun List<EventResponse>.toEventEntities(): List<EventEntity> {
        return this.map { mapToEventEntity(it) }
    }

    fun mapToSocialEntity(socialResponse: SocialResponse): SocialEntity {
        return SocialEntity(
            x = socialResponse.x ?: "",
            youtube = socialResponse.youtube ?: "",
            instagram = socialResponse.instagram ?: "",
            linkedin = socialResponse.linkedin ?: "",
            mastodon = socialResponse.mastodon ?: "",
            bluesky = socialResponse.bluesky ?: ""
        )
    }

    fun emptySocialResponse() = SocialResponse(
        x = "",
        youtube = "",
        instagram = "",
        linkedin = "",
        mastodon = "",
        bluesky = ""
    )
}