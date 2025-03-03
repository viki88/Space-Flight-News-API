package com.vikination.spaceflightnewsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.spaceflightnewsapp.data.models.News
import com.vikination.spaceflightnewsapp.data.models.NewsResponse
import com.vikination.spaceflightnewsapp.data.models.NewsType
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import com.vikination.spaceflightnewsapp.domain.repositories.SpaceFlightNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.http.Query
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

    private val _selectedNews = MutableStateFlow<List<News>>(emptyList())
    val selectedNews : StateFlow<List<News>> = _selectedNews

    private val _currentSelectedNews = MutableStateFlow(NewsType.ARTICLE.value)
    val currentSelectedNews : StateFlow<String> = _currentSelectedNews

    fun setCurrentSelectedNews(type :String){
        _currentSelectedNews.value = type
    }

    fun loadAllNews(forceRefresh: Boolean = false) {
        if (!forceRefresh && _articles.value.isNotEmpty() && _blogs.value.isNotEmpty() && _reports.value.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            coroutineScope {
                launch { loadArticles() }
                launch { loadBlogs() }
                launch { loadReports() }
            }
        }
    }

    suspend fun loadArticles(query: String? = null){
        spaceFlightNewsRepository.getArticles(query).collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _articles.value = (result.data as NewsResponse).results
                    if (_currentSelectedNews.value == NewsType.ARTICLE.value) _selectedNews.value = _articles.value
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

    suspend fun loadBlogs(query: String? = null){
        spaceFlightNewsRepository.getBlogs(query).collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _blogs.value = (result.data as NewsResponse).results
                    if (_currentSelectedNews.value == NewsType.BLOG.value) _selectedNews.value = _blogs.value
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

    suspend fun loadReports(query: String? = null){
        spaceFlightNewsRepository.getReports(query).collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _reports.value = (result.data as NewsResponse).results
                    if (_currentSelectedNews.value == NewsType.REPORT.value) _selectedNews.value = _reports.value
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

    fun loadNews(){
        viewModelScope.launch {
            when(_currentSelectedNews.value){
                NewsType.ARTICLE.value -> {
                    loadArticles()
                }
                NewsType.BLOG.value -> {
                    loadBlogs()
                }
                NewsType.REPORT.value -> {
                    loadReports()
                }

            }
        }
    }

    fun loadNewsByQuery(query :String){
        viewModelScope.launch {
            when(_currentSelectedNews.value){
                NewsType.ARTICLE.value -> {
                    loadArticles(query)
                }
                NewsType.BLOG.value -> {
                    loadBlogs(query)
                }
                NewsType.REPORT.value -> {
                    loadReports(query)
                }
            }
        }
    }


}