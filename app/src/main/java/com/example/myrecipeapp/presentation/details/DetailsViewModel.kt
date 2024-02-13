package com.example.myrecipeapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.remote.dto.MealResponse
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.use_cases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

    var meal = mutableStateOf(MealResponse(emptyList()))

    suspend fun getMeals(idMeal: String){
        meal.value = recipeUseCases.getMealUseCase(idMeal)
    }

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.UpsertDeleteMeal ->{
                viewModelScope.launch {
                    val meal = recipeUseCases.getSelectedMealUseCase(event.meal.idMeal)
                    if (meal == null){
                        upsertArticle(event.meal)
                    } else {
                        deleteArticle(event.meal)
                    }
                }
            }

            is DetailsEvent.RemoveSideEffect ->{
                sideEffect = null
            }
        }
    }

    private suspend fun deleteArticle(meal: Meal) {
        recipeUseCases.deleteMealUseCase(meal)
        sideEffect = "Meal Deleted"
    }

    private suspend fun upsertArticle(meal: Meal) {
        recipeUseCases.upsertMealUseCase(meal)
        sideEffect = "Meal Saved"
    }
}