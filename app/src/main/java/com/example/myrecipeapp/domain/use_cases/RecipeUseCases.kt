package com.example.myrecipeapp.domain.use_cases

data class RecipeUseCases(
    val getCategoryUseCase: GetCategoryUseCase,
    val getIngredientUseCase: GetIngredientUseCase,
    val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    val getMealsByRegionUseCase: GetMealsByRegionUseCase,
    val getMealsByIngredientUseCase: GetMealsByIngredientUseCase,
    val getMealUseCase: GetMealUseCase
)
