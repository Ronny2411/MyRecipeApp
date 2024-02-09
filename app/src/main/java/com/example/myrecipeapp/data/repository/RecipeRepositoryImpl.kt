package com.example.myrecipeapp.data.repository

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myrecipeapp.data.local.RecipeDao
import com.example.myrecipeapp.data.remote.CategoryPagingSource
import com.example.myrecipeapp.data.remote.FilterCategoryPagingSource
import com.example.myrecipeapp.data.remote.FilterIngredientPagingSource
import com.example.myrecipeapp.data.remote.FilterRegionPagingSource
import com.example.myrecipeapp.data.remote.IngredientsPagingSource
import com.example.myrecipeapp.data.remote.RecipeApi
import com.example.myrecipeapp.data.remote.dto.MealResponse
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.model.Ingredient
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi,
    private val recipeDao: RecipeDao
) : RecipeRepository{
    override fun getCategories(): Flow<PagingData<Category>> {
        return Pager(
            config = PagingConfig(pageSize = 14),
            pagingSourceFactory = {
                CategoryPagingSource(
                    recipeApi = recipeApi
                )
            }
        ).flow
    }

    override fun getIngredients(): Flow<PagingData<Ingredient>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                IngredientsPagingSource(
                    recipeApi = recipeApi
                )
            }
        ).flow
    }

    override fun filterByCategory(category: String): Flow<PagingData<FilterMeal>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                FilterCategoryPagingSource(
                    recipeApi = recipeApi,
                    category = category
                )
            }
        ).flow
    }

    override fun filterByRegion(region: String): Flow<PagingData<FilterMeal>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                FilterRegionPagingSource(
                    recipeApi = recipeApi,
                    region = region
                )
            }
        ).flow
    }

    override fun filterByIngredient(ingredient: String): Flow<PagingData<FilterMeal>> {
        return Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = {
                FilterIngredientPagingSource(
                    recipeApi = recipeApi,
                    ingredient = ingredient
                )
            }
        ).flow
    }

    override suspend fun fetchMeal(idMeal: String): MealResponse{
        return recipeApi.getMeal(idMeal = idMeal)
    }

}