package com.example.myrecipeapp.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.Ingredient
import com.example.myrecipeapp.presentation.common.BigCardShimmer
import com.example.myrecipeapp.presentation.navgraph.navigateToListAllMealsScreen
import com.example.myrecipeapp.util.Constants
import com.example.myrecipeapp.util.Dimens

@Composable
fun ViewAllItems(
    text: String?,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navController: NavHostController,
    navigateBack:()->Unit
) {
    BackHandler {
        navigateBack()
    }
    val categories = sharedViewModel.categories.collectAsLazyPagingItems()
    val areaStr = sharedViewModel.strArea
    val areaIcon = sharedViewModel.areaIcon
    val ingredient = sharedViewModel.ingredient.collectAsLazyPagingItems()
    Column(modifier = Modifier.fillMaxSize(),
    ) {
        if (text != null) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            if (text == "Categories") {
                ViewAllCategoryList(category = categories) {
                    navigateToListAllMealsScreen(navController,text = it, item = "category")
                }
            }
            if (text == "Ingredients") {
                ViewAllIngredientList(ingredient = ingredient, onClick = {
                    navigateToListAllMealsScreen(navController,text = it, item = "ingredient")
                })
            }
            if (text == "Regions") {
                ViewAllAreaList(areaStr = areaStr, areaIcon = areaIcon, onClick = {
                    navigateToListAllMealsScreen(navController,text = it, item = "region")
                })
            }
        }
    }
}


@Composable
fun ViewAllCategoryList(
    category: LazyPagingItems<Category>,
    onClick:(String)->Unit
) {
    val result = handlePagingResultForAllCategory(item = category)
    if (result){
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            contentPadding = PaddingValues(Dimens.MediumPadding2),
            columns = GridCells.Fixed(2)
        ){
            items(count = category.itemCount){
                category[it]?.let {category->
                    ItemViewCard(imageUrl = "${Constants.CATEGORY_IMAGE_URL}${category.strCategory}.png",
                        text = category.strCategory,
                        onClick = { onClick(category.strCategory) })
                }
            }
        }
    }
}

@Composable
fun ViewAllIngredientList(
    ingredient: LazyPagingItems<Ingredient>,
    onClick:(String)->Unit
) {
    val result = handlePagingResultForAllIngredient(item = ingredient)
    if (result){
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            contentPadding = PaddingValues(Dimens.MediumPadding2),
            columns = GridCells.Fixed(2)
        ){
            items(count = ingredient.itemCount){
                ingredient[it]?.let {ingredient->
                    ItemViewCard(imageUrl = "${Constants.INGREDIENTS_IMAGE_URL}${ingredient.strIngredient}.png",
                        text = ingredient.strIngredient.trim(),
                        onClick = { onClick(ingredient.strIngredient) })
                }
            }
        }
    }
}

@Composable
fun ViewAllAreaList(
    areaStr: List<String>,
    areaIcon: List<String>,
    onClick:(String)->Unit
) {
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        contentPadding = PaddingValues(Dimens.MediumPadding2),
        columns = GridCells.Fixed(2)
    ){
        items(areaStr.size){index->
            ItemViewCard(imageUrl = "${Constants.AREA_IMAGE_URL}${areaIcon[index]}",
                text = areaStr[index],
                onClick = { onClick(areaStr[index]) }
            )
        }
    }
}



@Composable
fun ItemViewCard(
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
                    .padding(Dimens.SmallPadding)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.placeholder),
                placeholder = painterResource(id = R.drawable.placeholder)
            )
            Text(text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(bottom = Dimens.SmallPadding)
            )
        }
    }
}


@Composable
fun handlePagingResultForAllCategory(
    item: LazyPagingItems<Category>
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
            BigCardShimmer()
            false
        }
        error!=null->{
            false
        }
        else->true
    }
}

@Composable
fun handlePagingResultForAllIngredient(
    item: LazyPagingItems<Ingredient>
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
            BigCardShimmer()
            false
        }
        error!=null->{
            false
        }
        else->true
    }
}