package com.example.myrecipeapp.domain.use_cases

import androidx.paging.PagingData
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetCategoryUseCase(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<PagingData<Category>>{
        return recipeRepository.getCategories()
    }
}