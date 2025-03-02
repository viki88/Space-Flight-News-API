package com.vikination.spaceflightnewsapp.data.network

import com.vikination.spaceflightnewsapp.ui.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Auth0ApiService {

    @GET("oidc/logout")
    suspend fun logout(
        @Query("id_token_hint") tokenId :String,
        @Query("client_id") clientId :String,
        @Query("post_logout_redirect_uri") redirectUri :String = Constants.CALLBACK_URL_AUTH0
    ):Response<Void>
}