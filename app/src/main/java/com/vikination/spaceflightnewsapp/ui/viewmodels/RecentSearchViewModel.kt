package com.vikination.spaceflightnewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vikination.spaceflightnewsapp.domain.repositories.RecentSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentSearchViewModel @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) :ViewModel() {

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    init {
        loadRecentSearches()
    }

    fun loadRecentSearches() {
        viewModelScope.launch {
            _recentSearches.value = recentSearchRepository.getRecentSearches()
        }
    }

    fun addSearchQuery(query: String) {
        viewModelScope.launch {
            recentSearchRepository.addSearchQuery(query)
            loadRecentSearches()
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            recentSearchRepository.clearSearchHistory()
            _recentSearches.value = emptyList()
        }
    }
}