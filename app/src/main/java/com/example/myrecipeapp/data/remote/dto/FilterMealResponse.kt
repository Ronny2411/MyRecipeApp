package com.example.myrecipeapp.data.remote.dto

import com.example.myrecipeapp.domain.model.FilterMeal

data class FilterMealResponse(
    val meals: List<FilterMeal>
)