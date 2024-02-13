package com.example.myrecipeapp.presentation.navgraph

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.presentation.common.BottomNavigationItem
import com.example.myrecipeapp.presentation.common.RecipeBottomNavigation
import com.example.myrecipeapp.presentation.details.DetailsEvent
import com.example.myrecipeapp.presentation.home.ViewAllItems
import com.example.myrecipeapp.presentation.details.DetailsScreen
import com.example.myrecipeapp.presentation.details.DetailsViewModel
import com.example.myrecipeapp.presentation.favorites.FavoritesScreen
import com.example.myrecipeapp.presentation.favorites.FavoritesViewModel
import com.example.myrecipeapp.presentation.home.HomeScreen
import com.example.myrecipeapp.presentation.home.SharedViewModel
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
        Screen.FavoriteScreen.route-> 1
        else-> 0
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screen.HomeScreen.route ||
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
                            1 -> navigateToTab(navController, Screen.FavoriteScreen.route)
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
                val detailsViewModel: DetailsViewModel = hiltViewModel()
                if (detailsViewModel.sideEffect!=null){
                    Toast.makeText(LocalContext.current, detailsViewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    detailsViewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                val mealId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("mealId")
                val meal = navController.previousBackStackEntry?.savedStateHandle?.get<Meal>("meal")
                if (mealId != null) {
                    LaunchedEffect(key1 = mealId) {
                        detailsViewModel.getMeals(mealId)
                    }
                    val mealResponse by detailsViewModel.meal
                    val meals = mealResponse.meals.firstOrNull()
                    if (meals != null) {
                        DetailsScreen(meal= meals,navController,event = detailsViewModel::onEvent)
                    }
                } else {
                    if (meal != null) {
                        DetailsScreen(meal= meal,navController,event = detailsViewModel::onEvent)
                    }
                }

            }
            composable(Screen.FavoriteScreen.route){
                val viewModel: FavoritesViewModel = hiltViewModel()
                val meals = viewModel.state.value
                FavoritesScreen(meals = meals.meals,
                    onClick = {meal->
                        navigateToDetails(navController,meal)
                    },
                    navigateBack = { navController.popBackStack() })
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
                    LaunchedEffect(key1 = text){
                        viewModel.updateFilterText(text,item)
                    }
                    val filteredMeals = viewModel.mealList.value.collectAsLazyPagingItems()
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

fun navigateToDetails(navController: NavController, meal: Meal){
    navController.currentBackStackEntry?.savedStateHandle?.set("meal",meal)
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