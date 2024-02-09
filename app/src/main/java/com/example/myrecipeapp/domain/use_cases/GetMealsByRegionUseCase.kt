package com.example.myrecipeapp.domain.use_cases

import androidx.paging.PagingData
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetMealsByRegionUseCase(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(region: String): Flow<PagingData<FilterMeal>> {
        return recipeRepository.filterByRegion(region = region)
    }
}