package com.example.myrecipeapp.presentation.details

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.util.Constants.MIN_BACKGROUND_IMAGE_HEIGHT
import com.example.myrecipeapp.util.Dimens
import com.example.myrecipeapp.util.Dimens.EXPANDED_RADIUS_LEVEL
import com.example.myrecipeapp.util.Dimens.EXTRA_LARGE_PADDING
import com.example.myrecipeapp.util.Dimens.INFO_ICON_SIZE
import com.example.myrecipeapp.util.Dimens.LARGE_PADDING
import com.example.myrecipeapp.util.Dimens.MEDIUM_PADDING
import com.example.myrecipeapp.util.Dimens.MIN_SHEET_HEIGHT
import com.example.myrecipeapp.util.Dimens.SMALL_PADDING

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsScreen(
    mealId: String,
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = mealId){
        detailsViewModel.getMeals(mealId)
    }
    val context = LocalContext.current
    val mealResponse by detailsViewModel.meal
    val meal = mealResponse.meals.firstOrNull()
    if (meal != null){
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        )

        val currentSheetFraction = scaffoldState.currentSheetFraction

        val radiusAnim by animateDpAsState(
            targetValue = if (currentSheetFraction == 1f)
                EXTRA_LARGE_PADDING
            else
                EXPANDED_RADIUS_LEVEL, label = ""
        )

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = MIN_SHEET_HEIGHT,
            sheetShape = RoundedCornerShape(topStart = radiusAnim, topEnd = radiusAnim),
            sheetContent = {
                BottomSheetContent(
                    selectedMeal = meal
                )
            }) {
            meal.strMealThumb?.let { it1 ->
                BackgroundContent(mealImage = it1,
                    //imageFraction = currentSheetFraction+MIN_BACKGROUND_IMAGE_HEIGHT
                ) {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedMeal: Meal,
    infoBoxIconColor: Color = androidx.compose.material.MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = if (isSystemInDarkTheme()) Color.Black else Color.White,
    contentColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            androidx.compose.material.Icon(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = stringResource(id = R.string.app_logo),
//                tint = contentColor,
//                modifier = Modifier
//                    .size(INFO_ICON_SIZE)
//                    .weight(2f)
//            )
            selectedMeal.strMeal?.let {
                Text(text = it,
                    color = contentColor,
                    fontSize = androidx.compose.material.MaterialTheme.typography.h4.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(8f))
            }
        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = MEDIUM_PADDING),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            InfoBox(icon = painterResource(id = R.drawable.bolt),
//                iconColor = infoBoxIconColor,
//                bigText = "${selectedHero.power}",
//                smallText = stringResource(R.string.power),
//                textColor = contentColor)
//            InfoBox(icon = painterResource(id = R.drawable.calendar),
//                iconColor = infoBoxIconColor,
//                bigText = selectedHero.month,
//                smallText = stringResource(R.string.month),
//                textColor = contentColor)
//            InfoBox(icon = painterResource(id = R.drawable.cake),
//                iconColor = infoBoxIconColor,
//                bigText = selectedHero.day,
//                smallText = stringResource(R.string.birthday),
//                textColor = contentColor)
//        }
        androidx.compose.material.Text(
            text = "Instructions",
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = androidx.compose.material.MaterialTheme.typography.subtitle1.fontSize,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SMALL_PADDING)
        )
        selectedMeal.strInstructions?.let {
            Text(text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(ContentAlpha.medium)
                    .padding(bottom = MEDIUM_PADDING)
                    .verticalScroll(rememberScrollState()),
                color = contentColor,
                fontSize = androidx.compose.material.MaterialTheme.typography.body1.fontSize)
        }
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            OrderedList(title = stringResource(R.string.family),
//                items = selectedHero.family,
//                textColor = contentColor)
//            OrderedList(title = stringResource(R.string.abilities),
//                items = selectedHero.abilities,
//                textColor = contentColor)
//            OrderedList(title = stringResource(R.string.nature_types),
//                items = selectedHero.natureTypes,
//                textColor = contentColor)
//        }
    }
}

@Composable
fun BackgroundContent(
    mealImage: String,
    imageFraction: Float = 0.6f,
    backgroundColor: Color = androidx.compose.material.MaterialTheme.colors.surface,
    onCloseClicked:()->Unit,
) {
    val imageUrl = mealImage
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(id = R.drawable.placeholder),
        placeholder = painterResource(id = R.drawable.placeholder)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)) {
        Image(painter = painter,
            contentDescription = stringResource(id = R.string.meal_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(imageFraction)
                .align(Alignment.TopStart))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            androidx.compose.material.Icon(imageVector = Icons.Default.Close,
                tint = Color.White,
                contentDescription = stringResource(R.string.close),
                modifier = Modifier
                    .padding(SMALL_PADDING)
                    .size(INFO_ICON_SIZE)
                    .clickable {
                        onCloseClicked()
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val fraction = bottomSheetState.progress
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == BottomSheetValue.Collapsed &&
                    targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded &&
                    targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed &&
                    targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded &&
                    targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }