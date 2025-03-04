package com.vikination.spaceflightnewsapp.domain.models

import com.vikination.spaceflightnewsapp.data.models.Author
import com.vikination.spaceflightnewsapp.data.models.Event
import com.vikination.spaceflightnewsapp.data.models.Launch
import kotlinx.serialization.Serializable

@Serializable
data class News (
    val id :Int,
    val title :String,
    val authors :List<Author>,
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

fun emptyNews() =
    News(
        id = 0,
        title = "",
        authors = emptyList(),
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