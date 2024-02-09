package com.example.myrecipeapp.presentation.home

sealed class FilterEvent {
    data class UpdateSearchQuery(val searchQuery: String): FilterEvent()
    object filterMealsByCategory: FilterEvent()
    object filterMealsByRegion: FilterEvent()
    object filterMealsByIngredient: FilterEvent()
}