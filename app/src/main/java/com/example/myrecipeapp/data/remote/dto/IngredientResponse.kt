package com.example.myrecipeapp.data.remote.dto

import com.example.myrecipeapp.domain.model.Ingredient

data class IngredientResponse(
    val meals: List<Ingredient>
)