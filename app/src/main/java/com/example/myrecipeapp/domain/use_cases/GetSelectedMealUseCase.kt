package com.example.newsapp.domain.usecases.news

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository

class GetSelectedMealUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(idMeal: String): Meal?{
        return recipeRepository.getSelectedMeal(idMeal)
    }
}