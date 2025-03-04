package com.vikination.spaceflightnewsapp.data.models

sealed class RequestResponse {
    data class Success(val data: Any) : RequestResponse()
    data class Error(val message: String) : RequestResponse()
    data object Loading : RequestResponse()
}