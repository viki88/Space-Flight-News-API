package com.vikination.spaceflightnewsapp.ui.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val authRepository: AuthRepository) :ViewModel() {

    private val _authState = MutableStateFlow<AuthResponse>(AuthResponse.Loading())
    val authState :StateFlow<AuthResponse> = _authState

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
}