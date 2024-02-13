package com.example.myrecipeapp.domain.use_cases

import com.example.myrecipeapp.presentation.details.DetailsEvent
import com.example.newsapp.domain.usecases.news.DeleteMealUseCase
import com.example.newsapp.domain.usecases.news.GetMealsUseCase
import com.example.newsapp.domain.usecases.news.GetSelectedMealUseCase
import com.example.newsapp.domain.usecases.news.UpsertMealUseCase

data class RecipeUseCases(
    val getCategoryUseCase: GetCategoryUseCase,
    val getIngredientUseCase: GetIngredientUseCase,
    val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    val getMealsByRegionUseCase: GetMealsByRegionUseCase,
    val getMealsByIngredientUseCase: GetMealsByIngredientUseCase,
    val getMealUseCase: GetMealUseCase,
    val upsertMealUseCase: UpsertMealUseCase,
    val deleteMealUseCase: DeleteMealUseCase,
    val getMealsUseCase: GetMealsUseCase,
    val getSelectedMealUseCase: GetSelectedMealUseCase
)
