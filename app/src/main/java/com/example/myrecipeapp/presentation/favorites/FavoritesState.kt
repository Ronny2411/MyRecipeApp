package com.example.myrecipeapp.presentation.favorites

import com.example.myrecipeapp.domain.model.Meal

data class FavoritesState(
    val meals: List<Meal> = emptyList()
)