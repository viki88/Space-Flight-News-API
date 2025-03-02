package com.vikination.spaceflightnewsapp.data.repositories

import android.app.Activity
import com.auth0.android.result.Credentials
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.data.network.Auth0ApiService
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import com.vikination.spaceflightnewsapp.ui.utils.UserPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    private val authManager: AuthManager,
    private val auth0ApiService: Auth0ApiService,
    private val userPrefs: UserPrefs
    ) :AuthRepository{

    override fun login(activity: Activity): Flow<AuthResponse> = flow {
        emit(AuthResponse.Loading())
        try {
            val credentials = suspendCoroutine {
                authManager.login(
                    activity, onSuccess = {
                        credentials -> it.resume(credentials)
                }, onError = {
                        error -> it.resumeWithException(error)
                })
            }
            emit(AuthResponse.Success(credentials))
        }catch (e :Exception){
            emit(AuthResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun logout(activity: Activity): Flow<AuthResponse> = flow {
        emit(AuthResponse.Loading())
        try {
            suspendCoroutine {
                authManager.logout(
                    activity, onSuccess = {
                    it.resume(Unit)
                }, onError = {
                    error -> it.resumeWithException(error)
                })
            }
            emit(AuthResponse.Success(null))
        }catch (e :Exception){
            emit(AuthResponse.Error(e.message.toString()))
        }
    }

    override fun logoutInBackground(clientId :String): Flow<Boolean> = flow {
        val tokenId = authManager.getTokenId()
        val response = auth0ApiService.logout(tokenId, clientId)
        if (response.isSuccessful){
            userPrefs.setLogoutStatus(true)
            emit(true)
        }else emit(false)
    }.flowOn(Dispatchers.IO)

    override fun getUserInfo(): Flow<Credentials?> = authManager.getUserInfo()

    override fun getIsLogoutStatus(): Flow<Boolean> = flow {
        emit(userPrefs.getLogoutStatus())
    }.flowOn(Dispatchers.IO)

    override fun getAuthenticateStatus(): Flow<Boolean> = flow {
        emit(userPrefs.getAuthenticateStatus())
    }.flowOn(Dispatchers.IO)

    override fun saveAuthStatus(isAuthenticate: Boolean) :Flow<Boolean> = flow{
        userPrefs.setAuthenticateStatus(isAuthenticate)
        emit(userPrefs.getAuthenticateStatus())
    }

}