package com.example.myrecipeapp.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myrecipeapp.data.remote.dto.MealResponse
import com.example.myrecipeapp.domain.use_cases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel@Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    var meal = mutableStateOf(MealResponse(emptyList()))

    suspend fun getMeals(idMeal: String){
        meal.value = recipeUseCases.getMealUseCase(idMeal)
    }

}