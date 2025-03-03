package com.vikination.spaceflightnewsapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.vikination.spaceflightnewsapp.ui.components.SearchAppBar
import com.vikination.spaceflightnewsapp.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    type: String,
    newsViewModel: NewsViewModel = hiltViewModel()
){
    val news by newsViewModel.selectedNews.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val isLoading by newsViewModel.isLoading.collectAsState()

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
                },
                onSortSelected = { sort ->

                },
                onSearchClicked = {
                    newsViewModel.loadNewsByQuery(searchQuery)
                },
                listMenuFilter = listOf("news","blogs","reports")
            )
        }
    ) {
        padding ->
        Column(
            Modifier.padding(padding)
        ){
            LazyColumn {
                items(news.size){ index ->
                    Text(news[index].title?:"No Title")
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
                    modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center).size(80.dp)
                )
            }
        }
    }
}