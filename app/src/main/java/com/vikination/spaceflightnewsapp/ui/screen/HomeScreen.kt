package com.vikination.spaceflightnewsapp.ui.screen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.data.models.RequestResponse
import com.vikination.spaceflightnewsapp.ui.components.ImageItemList
import com.vikination.spaceflightnewsapp.ui.viewmodels.GreetingsViewModel
import com.vikination.spaceflightnewsapp.ui.viewmodels.HomeViewModel
import com.vikination.spaceflightnewsapp.ui.viewmodels.NewsViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    greetingViewModel: GreetingsViewModel = hiltViewModel(),
    newsViewModel: NewsViewModel = hiltViewModel()
){
    val activity = LocalActivity.current as Activity
    val authState = homeViewModel.authState.collectAsState()
    val userIsLoggedIn = homeViewModel.userIsLoggedIn.collectAsState()
    val authStatus = homeViewModel.userAuthStatus.collectAsState()
    val articles = newsViewModel.articles.collectAsState()
    val blogs = newsViewModel.blogs.collectAsState()
    val reports = newsViewModel.reports.collectAsState()
    val gretting = greetingViewModel.gretting.collectAsState()
    val isLoading = newsViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.loadArticles()
        newsViewModel.loadBlogs()
        newsViewModel.loadReports()
    }

    LaunchedEffect(authStatus.value, userIsLoggedIn.value) {
        Log.i("TAG", "HomeScreen: ${authStatus.value} & ${userIsLoggedIn.value}")
        if (authStatus.value && !userIsLoggedIn.value){
            homeViewModel.logout(activity)
        }
    }

    when(authState.value){
        is AuthResponse.Success -> {
            val successResponse = (authState.value as AuthResponse.Success)
            if (successResponse.credentials != null){
//                val userEmail = successResponse.credentials.user.email
                homeViewModel.updateUserLogin(true)
                homeViewModel.updateAuthStatus(true)
                homeViewModel.startTimerWorker()
            }else{
                homeViewModel.updateAuthStatus(false)
            }
        }
        is AuthResponse.Error -> {
            val errorMessage = (authState.value as AuthResponse.Error).message
            Log.i("TAG", "HomeScreen: error login $errorMessage")
        }
        is AuthResponse.Loading -> {}
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            text = gretting.value)
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            text = "Anonimous")
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right,
            text = "See more ..."
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(articles.value.size){ index ->
                ImageItemList(articles.value[index])
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right,
            text = "See more ..."
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(blogs.value.size){ index ->
                ImageItemList(blogs.value[index])
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Right,
            text = "See more ..."
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(reports.value.size){ index ->
                ImageItemList(reports.value[index])
            }
        }
//        Button(
//            modifier = Modifier.fillMaxWidth(),
//            onClick = { homeViewModel.login(activity) }
//        ){
//            Text("Login")
//        }
//        Button(
//            modifier = Modifier.fillMaxWidth(),
//            onClick = { homeViewModel.logout(activity) }
//        ){
//            Text("Logout")
//        }
//        Text(
//            if (userIsLoggedIn.value){
//                "User is logged in"
//            }else{
//                "User is not logged in"
//            }
//        )
    }
    if (isLoading.value){
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center).size(80.dp)
        )
    }
}
