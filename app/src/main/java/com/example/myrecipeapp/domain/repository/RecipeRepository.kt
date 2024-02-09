package com.example.myrecipeapp.domain.repository

import androidx.paging.PagingData
import com.example.myrecipeapp.data.remote.dto.MealResponse
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.model.Ingredient
import com.example.myrecipeapp.domain.model.Meal
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getCategories(): Flow<PagingData<Category>>

    fun getIngredients(): Flow<PagingData<Ingredient>>

    fun filterByCategory(category: String): Flow<PagingData<FilterMeal>>

    fun filterByRegion(region: String): Flow<PagingData<FilterMeal>>

    fun filterByIngredient(ingredient: String): Flow<PagingData<FilterMeal>>

    suspend fun fetchMeal(idMeal: String): MealResponse
}