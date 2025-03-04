package com.vikination.spaceflightnewsapp.data.models

import com.google.gson.annotations.SerializedName

data class NewsSiteListResponse(
    val version: String?,
    @SerializedName("news_sites") val newsSite: List<String>?
)