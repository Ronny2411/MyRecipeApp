package com.example.myrecipeapp.presentation.navgraph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myrecipeapp.presentation.common.BottomNavigationItem
import com.example.myrecipeapp.presentation.common.RecipeBottomNavigation
import com.example.myrecipeapp.presentation.home.ViewAllItems
import com.example.myrecipeapp.presentation.details.DetailsScreen
import com.example.myrecipeapp.presentation.favorites.FavoritesScreen
import com.example.myrecipeapp.presentation.home.HomeScreen
import com.example.myrecipeapp.presentation.home.SharedViewModel
import com.example.myrecipeapp.presentation.search.SearchScreen
import com.example.myrecipeapp.presentation.home.ListAllMeals

@Composable
fun NavGraph(navController : NavHostController){

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(
                icon = Icons.Default.Home,
                text = "Home"
            ),
            BottomNavigationItem(
                icon = Icons.Default.Search,
                text = "Search"
            ),
            BottomNavigationItem(
                icon = Icons.Default.FavoriteBorder,
                text = "Favorites"
            )
        )
    }

    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by remember {
        mutableStateOf(0)
    }
    selectedItem = when(backStackState?.destination?.route){
        Screen.HomeScreen.route-> 0
        Screen.SearchScreen.route-> 1
        Screen.FavoriteScreen.route-> 2
        else-> 0
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screen.HomeScreen.route ||
                backStackState?.destination?.route == Screen.SearchScreen.route ||
                backStackState?.destination?.route == Screen.FavoriteScreen.route
    }


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding(),
        bottomBar = {
            if (isBottomBarVisible){
                RecipeBottomNavigation(items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClicked = { index ->
                        when (index) {
                            0 -> navigateToTab(navController, Screen.HomeScreen.route)
                            1 -> navigateToTab(navController, Screen.SearchScreen.route)
                            2 -> navigateToTab(navController, Screen.FavoriteScreen.route)
                        }
                    }
                )
            }
        }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
            composable(Screen.HomeScreen.route){
                HomeScreen(navController)
            }
            composable(Screen.DetailScreen.route){
                val mealId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("mealId")
                if (mealId != null) {
                    DetailsScreen(mealId= mealId,navController)
                }
            }
            composable(Screen.SearchScreen.route){
                SearchScreen()
            }
            composable(Screen.FavoriteScreen.route){
                FavoritesScreen()
            }
            composable(Screen.ViewAllScreen.route){
                val text = navController.previousBackStackEntry?.savedStateHandle?.get<String>("text")
                ViewAllItems(text = text,
                    navController = navController) {
                    navController.popBackStack()
                }
            }
            composable(Screen.ListAllMealsScreen.route){
                val text =
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("text")
                val item =
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("item")
                if (text != null && item != null) {
                    val viewModel: SharedViewModel = hiltViewModel()
                    val filteredMeals = viewModel.updateFilterText(text,item).collectAsLazyPagingItems()
                    ListAllMeals(text = text,
                        filteredMeals = filteredMeals,
                        navigateToDetails = { navigateToDetails(navController,it) }){
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let { homeScreen->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

fun navigateToDetails(navController: NavController, mealId: String){
    navController.currentBackStackEntry?.savedStateHandle?.set("mealId",mealId)
    navController.navigate(Screen.DetailScreen.route)
}

fun navigateToViewAllScreen(navController: NavController, text: String){
    navController.currentBackStackEntry?.savedStateHandle?.set("text",text)
    navController.navigate(Screen.ViewAllScreen.route)
}

fun navigateToListAllMealsScreen(navController: NavController, text: String, item: String){
    navController.currentBackStackEntry?.savedStateHandle?.set("text",text)
    navController.currentBackStackEntry?.savedStateHandle?.set("item",item)
    navController.navigate(Screen.ListAllMealsScreen.route)
}