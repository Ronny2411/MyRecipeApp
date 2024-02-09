package com.example.myrecipeapp.domain.use_cases

import androidx.paging.PagingData
import com.example.myrecipeapp.domain.model.Ingredient
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetIngredientUseCase (
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<PagingData<Ingredient>> {
        return recipeRepository.getIngredients()
    }
}