package com.vikination.spaceflightnewsapp.data.models

import com.google.gson.annotations.SerializedName

data class NewsResponseModel(
    val id :Int?,
    val title :String?,
    val authorResponses :List<AuthorResponse>?,
    @SerializedName("image_url") val imageUrl :String?,
    val summary :String?,
    val url :String?,
    @SerializedName("news_site") val newsSite :String?,
    @SerializedName("published_at") val publishedAt :String?,
    @SerializedName("updated_at") val updatedAt :String?,
    val featured :Boolean?,
    val launchResponses :List<LaunchResponse>?,
    val eventResponses :List<EventResponse>?,
)

data class LaunchResponse(
    @SerializedName("launch_id") val id :String?,
    val provider :String?,
)

data class EventResponse(
    @SerializedName("event_id") val id :String?,
    val provider :String?,
)