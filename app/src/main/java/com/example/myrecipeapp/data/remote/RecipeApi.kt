package com.example.myrecipeapp.data.remote

import com.example.myrecipeapp.data.remote.dto.CategoriesResponse
import com.example.myrecipeapp.data.remote.dto.IngredientResponse
import com.example.myrecipeapp.data.remote.dto.MealResponse
import com.example.myrecipeapp.data.remote.dto.FilterMealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("list.php?c=list")
    suspend fun getCategories() : CategoriesResponse

    @GET("search.php")
    suspend fun searchMeal(
        @Query("s") searchQuery: String
    ) : MealResponse

    @GET("lookup.php")
    suspend fun getMeal(
        @Query("i") idMeal: String
    ) : MealResponse

    @GET("list.php?i=list")
    suspend fun getIngredients() : IngredientResponse

    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ) : FilterMealResponse

    @GET("filter.php")
    suspend fun filterByIngredient(
        @Query("i") ingredient: String
    ) : FilterMealResponse

    @GET("filter.php")
    suspend fun filterByArea(
        @Query("a") area: String
    ) : FilterMealResponse
}