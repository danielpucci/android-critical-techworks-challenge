package com.pt.criticaltechworkschallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pt.criticaltechworkschallenge.ui.detail.DetailScreen
import com.pt.criticaltechworkschallenge.ui.news_list.NewsScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.NewsList.route) {
        composable(Screen.NewsList.route) {
            NewsScreen(
                onArticleClick = { article ->
                    val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screen.NewsDetail.createRoute(encodedUrl))
                }
            )
        }

        composable(
            route = Screen.NewsDetail.route,
            arguments = listOf(navArgument("articleUrl") { type = NavType.StringType })
        ) {
            DetailScreen(navController = navController)
        }
    }
}