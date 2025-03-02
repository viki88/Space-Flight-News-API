package com.vikination.spaceflightnewsapp.data.models

import com.auth0.android.result.Credentials

sealed class AuthResponse {
    data class Success(val credentials: Credentials?) :AuthResponse()
    data class Loading(val message: String = "Loading") :AuthResponse()
    data class Error(val message: String) :AuthResponse()
}