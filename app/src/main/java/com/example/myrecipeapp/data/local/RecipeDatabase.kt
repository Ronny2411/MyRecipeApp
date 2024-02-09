package com.example.myrecipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myrecipeapp.domain.model.Meal

@Database(entities = [Meal::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {
    abstract val recipeDao: RecipeDao
}