package com.example.newsapp.domain.usecases.news

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository

class DeleteMealUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(meal: Meal){
        recipeRepository.deleteMeal(meal)
    }
}