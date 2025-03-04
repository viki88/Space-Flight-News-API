package com.vikination.spaceflightnewsapp.data.models

data class Author(
    val name :String?,
    val socials :Social?
)

data class Social(
    val x :String?,
    val youtube :String?,
    val instagram :String?,
    val linkedin :String?,
    val mastodon :String?,
    val bluesky :String?,
)