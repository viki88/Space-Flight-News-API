package com.vikination.spaceflightnewsapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.spaceflightnewsapp.data.mapper.NewsMapper
import com.vikination.spaceflightnewsapp.data.models.NewsResponse
import com.vikination.spaceflightnewsapp.data.models.NewsSiteListResponse
import com.vikination.spaceflightnewsapp.data.models.NewsType
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import com.vikination.spaceflightnewsapp.domain.models.News
import com.vikination.spaceflightnewsapp.domain.models.emptyNews
import com.vikination.spaceflightnewsapp.domain.repositories.SpaceFlightNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
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

    private val _selectedNewsResponseModel = MutableStateFlow<List<News>>(emptyList())
    val selectedNewsResponseModel : StateFlow<List<News>> = _selectedNewsResponseModel

    private val _currentSelectedNewsList = MutableStateFlow(NewsType.ARTICLE.value)
    private val currentSelectedNewsList : StateFlow<String> = _currentSelectedNewsList

    var selectedNews by mutableStateOf(emptyNews())

    private val _newsSites = MutableStateFlow<List<String>>(emptyList())
    val newsSites : StateFlow<List<String>> = _newsSites

    fun setCurrentSelectedNews(type :String){
        _currentSelectedNewsList.value = type
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

    private suspend fun loadArticles(
        query: String? = null,
        newsSite: String? = null,
        ordering: String? = null
    ){
        spaceFlightNewsRepository.getArticles(query, newsSite, ordering).collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _articles.value = NewsMapper.mapToNewsUiModelList((result.data as NewsResponse).results)
                    if (currentSelectedNewsList.value == NewsType.ARTICLE.value) _selectedNewsResponseModel.value = _articles.value
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

    private suspend fun loadBlogs(
        query: String? = null,
        newsSite: String? = null,
        ordering: String? = null
    ){
        spaceFlightNewsRepository.getBlogs(query, newsSite, ordering).collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _blogs.value = NewsMapper.mapToNewsUiModelList((result.data as NewsResponse).results)
                    if (currentSelectedNewsList.value == NewsType.BLOG.value) _selectedNewsResponseModel.value = _blogs.value
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

    private suspend fun loadReports(
        query: String? = null,
        newsSite: String? = null,
        ordering: String? = null
    ){
        spaceFlightNewsRepository.getReports(query, newsSite,ordering).collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _reports.value = NewsMapper.mapToNewsUiModelList((result.data as NewsResponse).results)
                    if (currentSelectedNewsList.value == NewsType.REPORT.value) _selectedNewsResponseModel.value = _reports.value
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

    fun loadNews(query :String? = null , newsSite :String? = null, ordering: String? = null){
        viewModelScope.launch {
            when(currentSelectedNewsList.value){
                NewsType.ARTICLE.value -> {
                    loadArticles(query, newsSite, ordering)
                }
                NewsType.BLOG.value -> {
                    loadBlogs(query, newsSite, ordering)
                }
                NewsType.REPORT.value -> {
                    loadReports(query, newsSite, ordering)
                }
            }
        }
    }

    suspend fun loadNewsSite() {
        spaceFlightNewsRepository.getNewsSites().collect{
                result ->
            when(result){
                is RequestResponse.Success -> {
                    _newsSites.value = (result.data as NewsSiteListResponse).newsSite ?: emptyList()
                    Log.i("TAG", "loadNewsSite: ${_newsSites.value}")
                    _isLoading.value = false
                }
                is RequestResponse.Error -> {
                    Log.i("TAG", "Load news sites: ${result.message}")
                    _isLoading.value = false
                }
                is RequestResponse.Loading -> {
                    _isLoading.value = true
                }
            }
        }
    }


}