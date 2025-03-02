package com.vikination.spaceflightnewsapp.domain.repositories

import android.app.Activity
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(activity :Activity) : Flow<AuthResponse>
    fun logout(activity :Activity) :Flow<AuthResponse>
    fun logoutInBackground(clientId :String) :Flow<Boolean>
}