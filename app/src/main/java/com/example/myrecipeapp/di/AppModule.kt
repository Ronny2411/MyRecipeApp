package com.example.myrecipeapp.di

import android.app.Application
import androidx.room.Room
import com.example.myrecipeapp.data.local.RecipeDao
import com.example.myrecipeapp.data.local.RecipeDatabase
import com.example.myrecipeapp.data.remote.RecipeApi
import com.example.myrecipeapp.data.repository.RecipeRepositoryImpl
import com.example.myrecipeapp.domain.repository.RecipeRepository
import com.example.myrecipeapp.domain.use_cases.GetCategoryUseCase
import com.example.myrecipeapp.domain.use_cases.GetIngredientUseCase
import com.example.myrecipeapp.domain.use_cases.GetMealUseCase
import com.example.myrecipeapp.domain.use_cases.GetMealsByCategoryUseCase
import com.example.myrecipeapp.domain.use_cases.GetMealsByIngredientUseCase
import com.example.myrecipeapp.domain.use_cases.GetMealsByRegionUseCase
import com.example.myrecipeapp.domain.use_cases.RecipeUseCases
import com.example.myrecipeapp.util.Constants.BASE_URL
import com.example.myrecipeapp.util.Constants.RECIPE_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesRecipeApi(): RecipeApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeApi: RecipeApi,
        recipeDao: RecipeDao
    ): RecipeRepository = RecipeRepositoryImpl(recipeApi = recipeApi, recipeDao = recipeDao)

    @Provides
    @Singleton
    fun provideRecipeDatabase(
        application: Application
    ): RecipeDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = RecipeDatabase::class.java,
            name = RECIPE_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(
        recipeDatabase: RecipeDatabase
    ): RecipeDao = recipeDatabase.recipeDao

    @Provides
    @Singleton
    fun provideRecipeUseCases(
        recipeRepository: RecipeRepository
    ) = RecipeUseCases(
        getCategoryUseCase = GetCategoryUseCase(recipeRepository),
        getIngredientUseCase = GetIngredientUseCase(recipeRepository),
        getMealsByCategoryUseCase = GetMealsByCategoryUseCase(recipeRepository),
        getMealsByRegionUseCase = GetMealsByRegionUseCase(recipeRepository),
        getMealsByIngredientUseCase = GetMealsByIngredientUseCase(recipeRepository),
        getMealUseCase = GetMealUseCase(recipeRepository)
    )
}