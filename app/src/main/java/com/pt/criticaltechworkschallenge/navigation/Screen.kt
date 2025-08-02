package com.pt.criticaltechworkschallenge.navigation

sealed class Screen(val route: String) {
    data object NewsList : Screen("news_list")
    data object NewsDetail : Screen("news_detail/{articleUrl}") {
        fun createRoute(articleUrl: String) = "news_detail/$articleUrl"
    }
}