package com.example.myrecipeapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myrecipeapp.domain.model.Category

class CategoryPagingSource(
    private val recipeApi: RecipeApi
): PagingSource<Int, Category>() {

    private var totalCategoryCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        val page = params.key ?: 1
        return try {
            val categoriesResponse = recipeApi.getCategories()
            totalCategoryCount+=categoriesResponse.meals.size
            val category = categoriesResponse.meals
            LoadResult.Page(
                data = category,
                nextKey = if (totalCategoryCount == categoriesResponse.meals.size) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }
}