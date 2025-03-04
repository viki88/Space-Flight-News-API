package com.vikination.spaceflightnewsapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vikination.spaceflightnewsapp.ui.screen.DetailScreen
import com.vikination.spaceflightnewsapp.ui.screen.HomeScreen
import com.vikination.spaceflightnewsapp.ui.screen.ListScreen
import com.vikination.spaceflightnewsapp.ui.viewmodels.NewsViewModel

@Composable
fun MainNavHost(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    newsViewModel :NewsViewModel = hiltViewModel()
){
    NavHost(
        navController = navController,
        startDestination = Route.HOME.route,
        modifier = modifier
    ){

        composable(Route.HOME.route){
            HomeScreen(navController)
        }
        composable(
            Route.LIST.route,
            arguments = listOf(navArgument("type"){
                type = NavType.StringType
            })
        ){
            val type = it.arguments?.getString("type") ?: ""
            ListScreen(navController, type, newsViewModel)
        }
        composable(
            Route.DETAIL.route,
        ){
            DetailScreen(navController::popBackStack, newsViewModel)
        }
    }
}

sealed class Route(val route: String){
    data object HOME: Route("home")
    data object LIST: Route("list/{type}")
    data object DETAIL: Route("detail")
}