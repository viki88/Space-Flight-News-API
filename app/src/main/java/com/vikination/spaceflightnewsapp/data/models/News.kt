package com.vikination.spaceflightnewsapp.data.models

import com.google.gson.annotations.SerializedName

data class News(
    val id :Int?,
    val title :String?,
    val authors :List<Author>?,
    @SerializedName("image_url") val imageUrl :String?,
    val summary :String?,
    val url :String?,
    @SerializedName("news_site") val newsSite :String?,
    @SerializedName("published_at") val publishedAt :String?,
    @SerializedName("updated_at") val updatedAt :String?,
    val featured :Boolean?,
    val launches :List<Launch>?,
    val events :List<Event>?,
)

data class Launch(
    @SerializedName("launch_id") val id :String?,
    val provider :String?,
)

data class Event(
    @SerializedName("event_id") val id :String?,
    val provider :String?,

)