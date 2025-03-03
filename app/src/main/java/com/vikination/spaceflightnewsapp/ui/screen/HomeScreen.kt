package com.vikination.spaceflightnewsapp.ui.screen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.ui.components.HeaderLabel
import com.vikination.spaceflightnewsapp.ui.components.ImageItemList
import com.vikination.spaceflightnewsapp.ui.theme.Route
import com.vikination.spaceflightnewsapp.ui.viewmodels.GreetingsViewModel
import com.vikination.spaceflightnewsapp.ui.viewmodels.HomeViewModel
import com.vikination.spaceflightnewsapp.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    greetingViewModel: GreetingsViewModel = hiltViewModel(),
    newsViewModel: NewsViewModel = hiltViewModel()
){
    val activity = LocalActivity.current as Activity
    val authState by homeViewModel.authState.collectAsState()
    val userIsLoggedIn by homeViewModel.userIsLoggedIn.collectAsState()
    val authStatus by homeViewModel.userAuthStatus.collectAsState()
    val articles by newsViewModel.articles.collectAsState()
    val blogs by newsViewModel.blogs.collectAsState()
    val reports by newsViewModel.reports.collectAsState()
    val gretting by greetingViewModel.gretting.collectAsState()
    val isLoading by newsViewModel.isLoading.collectAsState()
    val userCredential by homeViewModel.userCredentials.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.loadAllNews()
    }

    LaunchedEffect(authStatus, userIsLoggedIn) {
        Log.i("TAG", "HomeScreen: $authStatus & $userIsLoggedIn")
        if (authStatus && !userIsLoggedIn){
            homeViewModel.logout(activity)
        }
    }

    when(authState){
        is AuthResponse.Success -> {
            val successResponse = (authState as AuthResponse.Success)
            if (successResponse.credentials != null){
//                val userEmail = successResponse.credentials.user.email
                homeViewModel.updateUserLogin(true)
                homeViewModel.updateAuthStatus(true)
                homeViewModel.startTimerWorker()
            }else{
                homeViewModel.updateAuthStatus(false)
            }
            homeViewModel.loadUserInfo()
        }
        is AuthResponse.Error -> {
            val errorMessage = (authState as AuthResponse.Error).message
            Log.i("TAG", "HomeScreen: error login $errorMessage")
        }
        is AuthResponse.Loading -> {}
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(text = "Space Flight News")
                }
            )
        }
    ) {
        padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)){
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = gretting)
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = userCredential?.user?.name ?: "Anonimous")
                if (!userIsLoggedIn){
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { homeViewModel.login(activity) }
                    ){
                        Text("Login")
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                HeaderLabel("Article",
                    onClickMore = {
                        navController.navigate(Route.LIST.route)
                    }
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(articles.size){ index ->
                        ImageItemList(articles[index])
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                HeaderLabel("Blog",
                    onClickMore = {
                        navController.navigate(Route.LIST.route)
                    }
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(blogs.size){ index ->
                        ImageItemList(blogs[index])
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                HeaderLabel("Report",
                    onClickMore = {
                        navController.navigate(Route.LIST.route)
                    }
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(reports.size){ index ->
                        ImageItemList(reports[index])
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        newsViewModel.loadAllNews(true)
                    }
                ) {
                    Text("Refresh")
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
}
