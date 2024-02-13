package com.example.myrecipeapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.domain.use_cases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {
    val categories = recipeUseCases.getCategoryUseCase().cachedIn(viewModelScope)
    val ingredient = recipeUseCases.getIngredientUseCase().cachedIn(viewModelScope)
    val strArea: List<String> = listOf("American",
        "British","Canadian","Chinese","Croatian",
        "Dutch","Egyptian","Filipino","French","Greek",
        "Indian","Irish","Italian","Jamaican","Japanese",
        "Kenyan","Malaysian","Mexican","Moroccan","Polish",
        "Portuguese","Russian","Spanish","Thai","Tunisian",
        "Turkish","Vietnamese")
    val areaIcon : List<String> = listOf("us.png",
        "gb.png","ca.png","cn.png","hr.png",
        "nl.png","eg.png","ph.png","fr.png","gr.png",
        "in.png","ie.png","it.png","jm.png","jp.png",
        "kn.png","my.png","mx.png","ma.png","pl.png",
        "pt.png","ru.png","es.png","th.png","tn.png",
        "tr.png","vn.png")

    val mealList = mutableStateOf<Flow<PagingData<FilterMeal>>>(emptyFlow())
    fun updateFilterText(text: String, item: String){
        when(item){
            "category"->{
                mealList.value = recipeUseCases.getMealsByCategoryUseCase(text).cachedIn(viewModelScope)
            }
            "region"->{
                mealList.value = recipeUseCases.getMealsByRegionUseCase(text).cachedIn(viewModelScope)
            }
            "ingredient"->{
                mealList.value = recipeUseCases.getMealsByIngredientUseCase(text).cachedIn(viewModelScope)
            }
        }
    }

}