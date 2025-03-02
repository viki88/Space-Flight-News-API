package com.vikination.spaceflightnewsapp.data.repositories

import android.app.Activity
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.data.network.Auth0ApiService
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
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
    private val auth0ApiService: Auth0ApiService
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
        emit(response.isSuccessful)
    }.flowOn(Dispatchers.IO)

}