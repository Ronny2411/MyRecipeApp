package com.example.myrecipeapp.presentation.favorites

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.presentation.home.MealViewCard
import com.example.myrecipeapp.presentation.navgraph.Screen
import com.example.myrecipeapp.util.Dimens
import com.example.myrecipeapp.util.Dimens.MediumPadding1
import com.example.myrecipeapp.util.Dimens.NAVIGATION_BAR_HEIGHT

@Composable
fun FavoritesScreen(
    meals: List<Meal>,
    onClick:(Meal)->Unit,
    navigateBack:()->Unit
) {
    BackHandler {
        navigateBack()
    }
    Column(modifier = Modifier.fillMaxSize().
    padding(bottom = NAVIGATION_BAR_HEIGHT)) {
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.displayMedium
                .copy(fontWeight = FontWeight.Bold)
        )
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding1),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(Dimens.MediumPadding2),
            columns = GridCells.Fixed(1)
        ) {
            items(count = meals.size) {
                val meal = meals[it]
                meal.strMealThumb?.let { it1 ->
                    meal.strMeal?.let { it2 ->
                        MealViewCard(imageUrl = it1,
                            text = it2,
                            onClick = { onClick(meal) })
                    }
                }
            }
        }
    }
}