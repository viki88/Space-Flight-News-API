package com.vikination.spaceflightnewsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.spaceflightnewsapp.data.models.News
import com.vikination.spaceflightnewsapp.data.models.NewsResponse
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import com.vikination.spaceflightnewsapp.domain.repositories.SpaceFlightNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val spaceFlightNewsRepository: SpaceFlightNewsRepository
) :ViewModel(){

    private val _articles = MutableStateFlow<List<News>>(emptyList())
    val articles : StateFlow<List<News>> = _articles

    private val _blogs = MutableStateFlow<List<News>>(emptyList())
    val blogs : StateFlow<List<News>> = _blogs

    private val _reports = MutableStateFlow<List<News>>(emptyList())
    val reports : StateFlow<List<News>> = _reports

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    fun loadArticles(){
        viewModelScope.launch {
            spaceFlightNewsRepository.getArticles().collect{
                    result ->
                when(result){
                    is RequestResponse.Success -> {
                        _articles.value = (result.data as NewsResponse).results
                        _isLoading.value = false
                    }
                    is RequestResponse.Error -> {
                        Log.i("TAG", "loadArticles: ${result.message}")
                        _isLoading.value = false
                    }
                    is RequestResponse.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }

    fun loadBlogs(){
        viewModelScope.launch {
            spaceFlightNewsRepository.getBlogs().collect{
                result ->
                when(result){
                    is RequestResponse.Success -> {
                        _blogs.value = (result.data as NewsResponse).results
                        _isLoading.value = false
                    }
                    is RequestResponse.Error -> {
                        Log.i("TAG", "Load news: ${result.message}")
                        _isLoading.value = false
                    }
                    is RequestResponse.Loading -> {
                        _isLoading.value = true
                    }
                }
            }

        }
    }

    fun loadReports(){
        viewModelScope.launch {
            spaceFlightNewsRepository.getReports().collect{
                    result ->
                when(result){
                    is RequestResponse.Success -> {
                        _reports.value = (result.data as NewsResponse).results
                        _isLoading.value = false
                    }
                    is RequestResponse.Error -> {
                        Log.i("TAG", "Load news: ${result.message}")
                        _isLoading.value = false
                    }
                    is RequestResponse.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }


}