package com.vikination.spaceflightnewsapp.domain.repositories

import android.app.Activity
import com.auth0.android.result.Credentials
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(activity :Activity) : Flow<AuthResponse>
    fun logout(activity :Activity) :Flow<AuthResponse>
    fun logoutInBackground(clientId :String) :Flow<Boolean>
    fun getUserInfo() :Flow<Credentials?>
    fun getIsLogoutStatus() :Flow<Boolean>
    fun getAuthenticateStatus() :Flow<Boolean>
    fun saveAuthStatus(isAuthenticate :Boolean) :Flow<Boolean>
}