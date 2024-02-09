package com.example.myrecipeapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.model.Meal

class FilterCategoryPagingSource(
    private val recipeApi: RecipeApi,
    private val category: String
): PagingSource<Int, FilterMeal>() {

    private var totalMealCount = 0

    override fun getRefreshKey(state: PagingState<Int, FilterMeal>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilterMeal> {
        val page = params.key ?: 1
        return try {
            val mealResponse = recipeApi.filterByCategory(category = category)
            totalMealCount+=mealResponse.meals.size
            val meals = mealResponse.meals
            LoadResult.Page(
                data = meals,
                nextKey = if (totalMealCount == mealResponse.meals.size) null else page + 1,
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