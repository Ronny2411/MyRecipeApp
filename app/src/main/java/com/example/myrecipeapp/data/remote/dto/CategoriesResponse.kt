package com.example.myrecipeapp.data.remote.dto

import com.example.myrecipeapp.domain.model.Category

data class CategoriesResponse(
    val meals : List<Category>
)
