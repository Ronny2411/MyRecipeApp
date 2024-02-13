package com.example.newsapp.domain.usecases.news

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetMealsUseCase(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<List<Meal>>{
        return recipeRepository.getMeals()
    }
}