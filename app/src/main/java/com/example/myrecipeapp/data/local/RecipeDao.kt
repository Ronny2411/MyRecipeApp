package com.example.myrecipeapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipeapp.domain.model.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM Meal")
    fun getMeals(): Flow<List<Meal>>

    @Query("SELECT * FROM Meal WHERE idMeal=:idMeal")
    suspend fun getSelectedMeal(idMeal: String): Meal?

}