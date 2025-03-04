package com.vikination.spaceflightnewsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vikination.spaceflightnewsapp.data.models.Event
import com.vikination.spaceflightnewsapp.data.models.Launch

@Composable
fun LaunchEventComponent(
    launch: List<Launch>?,
    event: List<Event>?
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.2f),// Top
                        Color.Black.copy(alpha = 0.4f),
                        Color.Black.copy(alpha = 0.6f), // middle
                        Color.Black.copy(alpha = 0.8f), // bottom
                    )
                )
            )
    ){
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            launch?.let {
                Column(
                    Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text(
                        "Launches",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column {
                        launch.forEach{
                            Text(
                                it.provider ?: "",
                                style = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
            if (launch != null && event !=null) Spacer(modifier = Modifier.width(8.dp))
            event?.let {
                Column(
                    Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text(
                        "Events",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Column {
                        event.forEach{
                            Text(
                                it.provider ?: "",
                                style = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}