package com.vikination.spaceflightnewsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class GreetingsViewModel @Inject constructor() :ViewModel(){

    private val _gretting = MutableStateFlow(getGretting())
    val gretting :MutableStateFlow<String> = _gretting

    init {
        updateGreetingEveryMinute()
    }

    fun getGretting() :String{
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when(hour){
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    fun updateGreetingEveryMinute(){
        viewModelScope.launch {
            while (true){
                delay(60_000)
                _gretting.value = getGretting()
            }
        }
    }


}