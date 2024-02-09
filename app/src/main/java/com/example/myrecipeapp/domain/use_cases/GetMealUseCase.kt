package com.example.myrecipeapp.domain.use_cases

import com.example.myrecipeapp.data.remote.dto.MealResponse
import com.example.myrecipeapp.domain.repository.RecipeRepository

class GetMealUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(idMeal: String): MealResponse {
        return recipeRepository.fetchMeal(idMeal = idMeal)
    }
}