package com.example.myrecipeapp.presentation.home

import androidx.paging.PagingData
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.model.Meal
import kotlinx.coroutines.flow.Flow

data class FilterState(
    var searchQuery: String = "",
    var meals: Flow<PagingData<FilterMeal>>? = null
)