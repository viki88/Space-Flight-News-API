package com.vikination.spaceflightnewsapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vikination.spaceflightnewsapp.ui.screen.HomeScreen
import com.vikination.spaceflightnewsapp.ui.utils.AuthManager

@Composable
fun MainNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        startDestination = Route.HOME.route,
        modifier = modifier
    ){
        composable(Route.HOME.route){
            HomeScreen(navController)
        }
    }
}

sealed class Route(val route: String){
    data object HOME: Route("home")
}