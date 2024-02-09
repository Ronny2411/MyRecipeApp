package com.example.myrecipeapp.presentation.details

import com.example.myrecipeapp.domain.model.Meal

data class RecipeState(
    val loading: Boolean = true,
    val list: List<Meal> = emptyList(),
    val error: String? = null
)
