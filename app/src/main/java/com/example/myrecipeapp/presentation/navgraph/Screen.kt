package com.example.myrecipeapp.presentation.navgraph

sealed class Screen(val route:String) {
    object HomeScreen: Screen("homescreen")
    object DetailScreen: Screen("detailscreen")
    object SearchScreen: Screen("searchscreen")
    object FavoriteScreen: Screen("favoritescreen")
    object ViewAllScreen: Screen("viewallscreen")
    object ListAllMealsScreen: Screen("listallmeals")
}