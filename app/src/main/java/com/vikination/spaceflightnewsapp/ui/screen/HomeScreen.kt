package com.vikination.spaceflightnewsapp.ui.screen

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager

@Composable
fun HomeScreen(
    authManager: AuthManager,
    navController: NavHostController
){
    val activity = LocalActivity.current as Activity

    Column {
        Text(text = "Home Screen")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                authManager.login(activity, onSuccess = {
                    credentials ->
                    Log.i("TAG", "Login Success : ${credentials.user.email}")
                }, onError = {
                    error ->
                    Log.i("TAG", "Login Error : $error")
                })
            }
        ){
            Text("Login")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                authManager.logout(activity, onSuccess = {
                    Log.i("TAG", "Logout Success : ")
                }, onError = {
                        error ->
                    Log.i("TAG", "Login Error : $error")
                })
            }
        ){
            Text("Logout")
        }
    }
}