package com.vikination.spaceflightnewsapp.data.repositories

import android.app.Activity
import android.util.Log
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(val authManager: AuthManager) :AuthRepository{

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

}