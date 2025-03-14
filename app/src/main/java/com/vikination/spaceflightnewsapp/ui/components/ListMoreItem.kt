package com.vikination.spaceflightnewsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vikination.spaceflightnewsapp.domain.models.News

@Composable
fun ListMoreItem(
    news: News,
    onSelectedNews: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxWidth().height(300.dp).clickable {
            onSelectedNews()
        }
    ){
        Card(
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter
            ){
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = news.imageUrl,
                    contentDescription = news.title,
                    modifier = Modifier.fillMaxSize()
                )
                if (news.launches.isNotEmpty() && news.events.isNotEmpty()){
                    LaunchEventComponent(
                        news.launches,
                        news.events
                    )
                }
            }
        }

    }
}