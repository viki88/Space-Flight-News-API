package com.vikination.spaceflightnewsapp.domain.models

data class News (
    val id :Int,
    val title :String,
    val authorResponses :List<Author>,
    val imageUrl :String,
    val summary :String,
    val url :String,
    val newsSite :String,
    val publishedAt :String,
    val updatedAt :String,
    val featured :Boolean,
    val launches :List<Launch>,
    val events :List<Event>,
)

data class Author(
    val name :String,
    val socials : Social
)

data class Social(
    val x :String,
    val youtube :String,
    val instagram :String,
    val linkedin :String,
    val mastodon :String,
    val bluesky :String,
)

data class Launch(
    val id :String,
    val provider :String,
)

data class Event(
    val id :String,
    val provider :String,
)

fun emptyNews() =
    News(
        id = 0,
        title = "",
        authorResponses = emptyList(),
        imageUrl = "",
        summary = "",
        url = "",
        newsSite = "",
        publishedAt = "",
        updatedAt = "",
        featured = false,
        launches = emptyList(),
        events = emptyList()
    )