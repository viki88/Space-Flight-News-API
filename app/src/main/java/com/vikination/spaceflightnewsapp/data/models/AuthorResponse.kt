package com.vikination.spaceflightnewsapp.data.models

import com.vikination.spaceflightnewsapp.data.source.local.entity.SocialEntity

data class AuthorResponse(
    val name :String?,
    val socials :SocialResponse?
)

data class SocialResponse(
    val x :String?,
    val youtube :String?,
    val instagram :String?,
    val linkedin :String?,
    val mastodon :String?,
    val bluesky :String?,
)