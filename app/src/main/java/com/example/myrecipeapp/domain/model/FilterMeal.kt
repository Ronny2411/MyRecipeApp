package com.example.myrecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterMeal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) : Parcelable