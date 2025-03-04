package com.vikination.spaceflightnewsapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.vikination.spaceflightnewsapp.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    newsViewModel: NewsViewModel
){
    val news = newsViewModel.selectedNews

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(text = "Space Flight News")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }
            )
        }
    ) {
        paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ){
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(300.dp),
                model = news.imageUrl,
                contentDescription = "News Image"
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = news.publishedAt,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = news.summary.substringBefore("."),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}