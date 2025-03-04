package com.vikination.spaceflightnewsapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vikination.spaceflightnewsapp.data.models.NewsResponseModel
import com.vikination.spaceflightnewsapp.domain.models.News

@Composable
fun ImageItemList(
    news: News
) {
    Card {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(100.dp).height(100.dp),
            model = news.imageUrl,
            contentDescription = "image-article"
        )
    }
}