package com.vikination.spaceflightnewsapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vikination.spaceflightnewsapp.data.source.local.NewsConverter

@Entity(tableName = "news")
@TypeConverters(NewsConverter::class)
data class NewsEntity(
    @PrimaryKey val id :Int,
    val title :String,
    val authors :List<AuthorEntity>,
    val imageUrl :String,
    val summary :String,
    val url :String,
    val newsSite :String,
    val publishedAt :String,
    val updatedAt :String,
    val featured :Boolean,
    val launches :List<LaunchEntity>,
    val events :List<EventEntity>,
    val type :String
)

data class AuthorEntity(
    val name :String,
    val socials : SocialEntity
)

data class SocialEntity(
    val x :String,
    val youtube :String,
    val instagram :String,
    val linkedin :String,
    val mastodon :String,
    val bluesky :String,
)

data class LaunchEntity(
    val id :String,
    val provider :String,
)

data class EventEntity(
    val id :String,
    val provider :String,
)

