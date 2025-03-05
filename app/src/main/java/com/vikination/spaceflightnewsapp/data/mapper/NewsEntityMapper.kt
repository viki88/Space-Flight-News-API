package com.vikination.spaceflightnewsapp.data.mapper

import com.vikination.spaceflightnewsapp.data.source.local.entity.AuthorEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.EventEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.LaunchEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.NewsEntity
import com.vikination.spaceflightnewsapp.data.source.local.entity.SocialEntity
import com.vikination.spaceflightnewsapp.domain.models.Author
import com.vikination.spaceflightnewsapp.domain.models.Event
import com.vikination.spaceflightnewsapp.domain.models.Launch
import com.vikination.spaceflightnewsapp.domain.models.News
import com.vikination.spaceflightnewsapp.domain.models.Social

object NewsEntityMapper {

    /**
     * mapper collection for NewsUiModel
     */
    fun List<NewsEntity>.toNewsUiModel(): List<News> {
        return this.map { mapToNewsUiModel(it) }
    }

    fun mapToNewsUiModel(newsEntity: NewsEntity): News {
        return News(
            id = newsEntity.id,
            title = newsEntity.title,
            authorResponses = newsEntity.authors.toAuthorUiModels(),
            imageUrl = newsEntity.imageUrl,
            summary = newsEntity.summary,
            url = newsEntity.url,
            newsSite = newsEntity.newsSite,
            publishedAt = newsEntity.publishedAt,
            updatedAt = newsEntity.updatedAt,
            featured = newsEntity.featured,
            launches = newsEntity.launches.toLauncheUiModels(),
            events = newsEntity.events.toEventUiModel()
        )
    }

    fun mapToAuthorUiModel(authorEntity: AuthorEntity): Author {
        return Author(
            name = authorEntity.name,
            socials = mapToSocialUiModel(authorEntity.socials)
        )
    }

    fun List<AuthorEntity>.toAuthorUiModels(): List<Author> {
        return this.map { mapToAuthorUiModel(it) }
    }

    fun mapToLaunchUiModel(launchEntity: LaunchEntity): Launch {
        return Launch(
            id = launchEntity.id,
            provider = launchEntity.provider
        )
    }

    fun List<LaunchEntity>.toLauncheUiModels(): List<Launch> {
        return this.map { mapToLaunchUiModel(it) }
    }

    fun mapToEventUiModel(eventEntity: EventEntity) : Event {
        return Event(
            id = eventEntity.id,
            provider = eventEntity.provider
        )
    }

    fun List<EventEntity>.toEventUiModel(): List<Event> {
        return this.map { mapToEventUiModel(it) }
    }

    fun mapToSocialUiModel(socialEntity: SocialEntity): Social {
        return Social(
            x = socialEntity.x,
            youtube = socialEntity.youtube,
            instagram = socialEntity.instagram,
            linkedin = socialEntity.linkedin,
            mastodon = socialEntity.mastodon,
            bluesky = socialEntity.bluesky
        )
    }

}