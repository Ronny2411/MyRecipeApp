package com.example.myrecipeapp.data.remote.dto

import com.example.myrecipeapp.domain.model.Meal

data class MealResponse(
    val meals: List<Meal>
)