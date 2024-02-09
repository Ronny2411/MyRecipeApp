package com.example.myrecipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val idIngredient: String? = null,
    val strIngredient: String,
    val strDescription: String? = null,
    val strType: String? = null
) : Parcelable