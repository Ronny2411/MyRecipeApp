package com.example.myrecipeapp.presentation.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.Ingredient
import com.example.myrecipeapp.presentation.common.CardShimmer
import com.example.myrecipeapp.presentation.common.SearchBar
import com.example.myrecipeapp.presentation.navgraph.Screen
import com.example.myrecipeapp.presentation.navgraph.navigateToListAllMealsScreen
import com.example.myrecipeapp.presentation.navgraph.navigateToViewAllScreen
import com.example.myrecipeapp.util.Constants
import com.example.myrecipeapp.util.Dimens
import com.example.myrecipeapp.util.Dimens.LogoHeight
import com.example.myrecipeapp.util.Dimens.LogoWidth
import com.example.myrecipeapp.util.Dimens.MediumPadding2
import com.example.myrecipeapp.util.Dimens.SmallPadding

@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel(),
) {
    val categories = sharedViewModel.categories.collectAsLazyPagingItems()
    val areaStr = sharedViewModel.strArea
    val areaIcon = sharedViewModel.areaIcon
    val ingredient = sharedViewModel.ingredient.collectAsLazyPagingItems()
    Column(modifier = Modifier.background(
        if (isSystemInDarkTheme()) Color.Black else Color.Transparent
    ).padding(bottom = Dimens.NAVIGATION_BAR_HEIGHT)
    ) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier
                .width(LogoWidth)
                .height(LogoHeight)
                .padding(horizontal = SmallPadding, vertical = SmallPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
                .weight(1f)
                .verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Categories",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black)
                Divider(thickness = 5.dp,
                    modifier = Modifier
                        .weight(1f))
                Text(text = "View All",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navigateToViewAllScreen(navController, "Categories") },
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            }
            CategoryList(category = categories, onClick = {
                navigateToListAllMealsScreen(navController, text = it, item = "category")
            })
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Regions",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black)
                Divider(thickness = 5.dp,
                    modifier = Modifier
                        .weight(1f))
                Text(text = "View All",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navigateToViewAllScreen(navController, "Regions") },
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            }
            AreaList(areaStr,areaIcon,onClick = {
                navigateToListAllMealsScreen(navController, text = it, item = "region")
            })
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Ingredients",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black)
                Divider(thickness = 5.dp,
                    modifier = Modifier
                        .weight(1f))
                Text(text = "View All",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navigateToViewAllScreen(navController, "Ingredients") },
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            }
            IngredientList(ingredient = ingredient, onClick = {
                navigateToListAllMealsScreen(navController, text = it, item = "ingredient")
            })
        }
    }
}

@Composable
fun CategoryList(
    category: LazyPagingItems<Category>,
    onClick:(String)->Unit
) {
    val result = handlePagingResultForCategory(item = category)
    if (result){
        LazyRow(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            contentPadding = PaddingValues(MediumPadding2)
        ){
            items(count = category.itemCount){
                category[it]?.let {category->
                    ItemCard(imageUrl = "${Constants.CATEGORY_IMAGE_URL}${category.strCategory}.png",
                        text = category.strCategory,
                        onClick = { onClick(category.strCategory) })
                }
            }
        }
    }
}

@Composable
fun IngredientList(
    ingredient: LazyPagingItems<Ingredient>,
    onClick:(String)->Unit
) {
    val result = handlePagingResultForIngredient(item = ingredient)
    if (result){
        LazyRow(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            contentPadding = PaddingValues(MediumPadding2)
        ){
            items(count = ingredient.itemCount){
                ingredient[it]?.let {ingredient->
                    ItemCard(imageUrl = "${Constants.INGREDIENTS_IMAGE_URL}${ingredient.strIngredient}.png",
                        text = ingredient.strIngredient.trim(),
                        onClick = { onClick(ingredient.strIngredient) })
                }
            }
        }
    }
}

@Composable
fun AreaList(
    areaStr: List<String>,
    areaIcon: List<String>,
    onClick:(String)->Unit
) {
    LazyRow(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        contentPadding = PaddingValues(MediumPadding2)
    ){
        items(areaStr.size){index->
            ItemCard(imageUrl = "${Constants.AREA_IMAGE_URL}${areaIcon[index]}",
                text = areaStr[index],
                onClick = { onClick(areaStr[index]) }
            )
        }
    }
}

@Composable
fun ItemCard(
    imageUrl: String,
    text: String,
    onClick:(String)->Unit
) {
    val context = LocalContext.current
    Card(shape = RoundedCornerShape(10),
        modifier = Modifier.clickable { onClick(text) }
            .wrapContentWidth()
            .wrapContentHeight()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = ImageRequest.Builder(context).data(imageUrl).build(),
                contentDescription = stringResource(R.string.item_image),
                modifier = Modifier.padding(SmallPadding)
                    .size(width = 100.dp, height = 100.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.placeholder),
                placeholder = painterResource(id = R.drawable.placeholder))
            Text(text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(bottom = SmallPadding)
            )
        }
    }
}


@Composable
fun handlePagingResultForCategory(
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
            CardShimmer()
            false
        }
        error!=null->{
            false
        }
        else->true
    }
}

@Composable
fun handlePagingResultForIngredient(
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
            CardShimmer()
            false
        }
        error!=null->{
            false
        }
        else->true
    }
}