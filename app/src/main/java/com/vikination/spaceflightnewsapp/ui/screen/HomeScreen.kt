package com.vikination.spaceflightnewsapp.ui.screen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vikination.spaceflightnewsapp.data.models.AuthResponse
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager
import com.vikination.spaceflightnewsapp.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    authManager: AuthManager,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
){
    val activity = LocalActivity.current as Activity
    val authState = homeViewModel.authState.collectAsState()

    when(authState.value){
        is AuthResponse.Success -> {
            val successResponse = (authState.value as AuthResponse.Success)
            if (successResponse.credentials != null){
                val userEmail = successResponse.credentials.user.email
                Log.i("TAG", "HomeScreen: user email $userEmail")
            }else{
                Log.i("TAG", "HomeScreen: user logout success")
            }
        }
        is AuthResponse.Error -> {
            val errorMessage = (authState.value as AuthResponse.Error).message
            Log.i("TAG", "HomeScreen: error login $errorMessage")
        }
        is AuthResponse.Loading -> {}
    }

    Column {
        Text(text = "Home Screen")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { homeViewModel.login(activity) }
        ){
            Text("Login")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { homeViewModel.logout(activity) }
        ){
            Text("Logout")
        }
    }
}