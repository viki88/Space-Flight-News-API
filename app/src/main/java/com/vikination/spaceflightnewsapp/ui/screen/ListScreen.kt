package com.vikination.spaceflightnewsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vikination.spaceflightnewsapp.ui.components.ListMoreItem
import com.vikination.spaceflightnewsapp.ui.components.SearchAppBar
import com.vikination.spaceflightnewsapp.ui.viewmodels.NewsViewModel
import com.vikination.spaceflightnewsapp.ui.viewmodels.RecentSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    type: String,
    newsViewModel: NewsViewModel = hiltViewModel(),
    recentSearchViewModel: RecentSearchViewModel = hiltViewModel()
){
    val news by newsViewModel.selectedNews.collectAsState()
    val recentSearches by recentSearchViewModel.recentSearches.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val isLoading by newsViewModel.isLoading.collectAsState()
    val newsSites by newsViewModel.newsSites.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.setCurrentSelectedNews(type)
        newsViewModel.loadNews()
    }

    Scaffold(
        topBar = {
            SearchAppBar(
                type = type,
                searchQuery = searchQuery,
                navController = navController,
                onSearchQueryChanged = { query ->
                    searchQuery = query
                },
                onFilterSelected = { filter ->
                    newsViewModel.loadNewsByFilterAndQuery(searchQuery, filter)
                },
                onSortSelected = { sort ->

                },
                onSearchClicked = {
                    newsViewModel.loadNewsByQuery(searchQuery)
                    recentSearchViewModel.addSearchQuery(searchQuery)
                    searchQuery = ""
                },
                onSelectedRecentSearch = {
                    query ->
                        newsViewModel.loadNewsByQuery(query)
                        recentSearchViewModel.addSearchQuery(query)
                },
                listMenuFilter = newsSites,
                recentSearches = recentSearches
            )
        }
    ) {
        padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(8.dp)
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(news.size){ index ->
                    ListMoreItem(news[index]){}
                }
            }
        }
        if (isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                        .size(80.dp)
                )
            }
        }
    }
}