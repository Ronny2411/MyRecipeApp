package com.example.myrecipeapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myrecipeapp.domain.model.Ingredient

class IngredientsPagingSource(
    private val recipeApi: RecipeApi
): PagingSource<Int, Ingredient>() {
    private var totalIngredientCount = 0
    override fun getRefreshKey(state: PagingState<Int, Ingredient>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Ingredient> {
        val page = params.key ?: 1
        return try {
            val ingredientResponse = recipeApi.getIngredients()
            totalIngredientCount+= ingredientResponse.meals.size ?: 0
            val ingredient = ingredientResponse.meals
            LoadResult.Page(
                data = ingredient,
                nextKey = if (totalIngredientCount == ingredientResponse.meals.size) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}