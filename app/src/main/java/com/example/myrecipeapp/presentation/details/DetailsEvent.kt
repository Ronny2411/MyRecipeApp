package com.example.myrecipeapp.presentation.details

import com.example.myrecipeapp.domain.model.Meal

sealed class DetailsEvent {
    data class UpsertDeleteMeal(val meal: Meal): DetailsEvent()
    object RemoveSideEffect: DetailsEvent()
}