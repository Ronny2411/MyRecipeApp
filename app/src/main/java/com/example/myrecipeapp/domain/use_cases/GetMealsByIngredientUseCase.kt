package com.example.myrecipeapp.domain.use_cases

import androidx.paging.PagingData
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetMealsByIngredientUseCase(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(ingredient: String): Flow<PagingData<FilterMeal>> {
        return recipeRepository.filterByIngredient(ingredient = ingredient)
    }
}