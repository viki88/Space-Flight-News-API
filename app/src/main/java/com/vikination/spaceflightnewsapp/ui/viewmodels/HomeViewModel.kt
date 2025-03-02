package com.vikination.spaceflightnewsapp.ui.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import com.vikination.spaceflightnewsapp.ui.utils.Constants
import com.vikination.spaceflightnewsapp.ui.utils.LogoutWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val workManager: WorkManager
) :ViewModel() {

    private val _authState = MutableStateFlow<AuthResponse>(AuthResponse.Loading())
    val authState :StateFlow<AuthResponse> = _authState

    private val _userIsLoggedIn = MutableStateFlow(false)
    val userIsLoggedIn :StateFlow<Boolean> = _userIsLoggedIn

    private val _userAuthStatus = MutableStateFlow(false)
    val userAuthStatus :StateFlow<Boolean> = _userAuthStatus

    init {
        getIsLogoutStatus()
    }

    fun getIsLogoutStatus(){
        viewModelScope.launch {
            authRepository.getIsLogoutStatus().collect{
                Log.i("TAG", "getIsLogoutStatus: $it")
                updateUserLogin(!it)
                getAuthStatus()
            }
        }
    }

    fun login(activity: Activity){
        viewModelScope.launch {
            authRepository.login(activity).collect{
                _authState.value = it
            }
        }
    }

    fun logout(activity: Activity){
        viewModelScope.launch {
            authRepository.logout(activity).collect{
                _authState.value = it
            }
        }
    }

    fun updateUserLogin(isLogin :Boolean){
        _userIsLoggedIn.value = isLogin
    }

    fun updateAuthStatus(isAuthenticate :Boolean){
        viewModelScope.launch {
            authRepository.saveAuthStatus(isAuthenticate).collect{
                _userAuthStatus.value = it
            }
        }
    }

    fun startTimerWorker() {
        val workRequest = OneTimeWorkRequestBuilder<LogoutWorker>()
            .setInitialDelay(
                Constants.TIMER_INTERVAL_IN_MINUTES.toLong(),
                TimeUnit.MINUTES
            )
            .build()
        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id).observeForever {
            workInfo ->
            if (workInfo?.state == WorkInfo.State.SUCCEEDED){
                updateUserLogin(false)
            }
        }

    }

    fun getAuthStatus() = viewModelScope.launch {
        authRepository.getAuthenticateStatus().collect{
            _userAuthStatus.value = it
        }
    }

}