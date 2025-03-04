package com.vikination.spaceflightnewsapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val name :String?,
    val socials :Social?
)

@Serializable
data class Social(
    val x :String?,
    val youtube :String?,
    val instagram :String?,
    val linkedin :String?,
    val mastodon :String?,
    val bluesky :String?,
)