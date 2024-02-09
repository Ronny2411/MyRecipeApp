package com.example.myrecipeapp.presentation.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.FilterMeal
import com.example.myrecipeapp.presentation.common.BigCardShimmer
import com.example.myrecipeapp.presentation.common.SingleCardShimmer
import com.example.myrecipeapp.util.Dimens
import com.example.myrecipeapp.util.Dimens.IconSize
import com.example.myrecipeapp.util.Dimens.MediumPadding2

@Composable
fun ListAllMeals(
    text: String,
    filteredMeals: LazyPagingItems<FilterMeal>,
    navigateToDetails: (String)-> Unit,
    navigateBack:()->Unit
) {
    BackHandler {
        navigateBack()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium
                .copy(fontWeight = FontWeight.Bold)
        )
        val result = handlePagingResultForFilterMeals(item = filteredMeals)
        if (result){
            LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
                verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
                contentPadding = PaddingValues(Dimens.MediumPadding2),
                columns = GridCells.Fixed(1)
            ){
                items(count = filteredMeals.itemCount){
                    filteredMeals[it]?.let {meal->
                        Log.d("meals",meal.toString())
                        MealViewCard(imageUrl = meal.strMealThumb,
                            text = meal.strMeal.trim(),
                            onClick = { navigateToDetails(meal.idMeal) })
                    }
                }
            }
        }
    }
}

@Composable
fun MealViewCard(
    imageUrl: String,
    text: String,
    onClick:(String)->Unit
) {
    val context = LocalContext.current
    Card(shape = RoundedCornerShape(10),
        modifier = Modifier
            .clickable { onClick(text) }
            .fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = ImageRequest.Builder(context).data(imageUrl).build(),
                contentDescription = stringResource(R.string.item_image),
                modifier = Modifier
                    .padding(bottom = MediumPadding2)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.placeholder),
                placeholder = painterResource(id = R.drawable.placeholder)
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(start = MediumPadding2, bottom = MediumPadding2)
                )
                IconButton(onClick = { /*TODO*/ },
                    modifier = Modifier.padding(bottom = MediumPadding2, end = MediumPadding2)) {
                    Icon(imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Save Icon")
                }
            }
        }
    }
}

@Composable
fun handlePagingResultForFilterMeals(
    item: LazyPagingItems<FilterMeal>
): Boolean {
    val loadState = item.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }
    return when{
        loadState.refresh is LoadState.Loading->{
            SingleCardShimmer()
            false
        }
        error!=null->{
            false
        }
        else->true
    }
}