package com.example.myrecipeapp.presentation.details

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import coil.compose.rememberAsyncImagePainter
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.Meal
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
    meal: Meal,
    navController: NavHostController,
    event: (DetailsEvent)->Unit
) {

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
    val context = LocalContext.current

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetShape = RoundedCornerShape(topStart = radiusAnim, topEnd = radiusAnim),
        sheetContent = {
            BottomSheetContent(
                selectedMeal = meal,
                onSavedClicked = {event(DetailsEvent.UpsertDeleteMeal(meal))},
                onBrowseClicked = {
                    Intent(Intent.ACTION_VIEW).also {
                        it.data = Uri.parse(meal.strYoutube)
                        if (it.resolveActivity(context.packageManager) != null){
                            context.startActivity(it)
                        }
                    }
                }
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

@Composable
fun BottomSheetContent(
    selectedMeal: Meal,
    iconColor: Color = androidx.compose.material.MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = if (isSystemInDarkTheme()) Color.Black else Color.White,
    contentColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black,
    onSavedClicked:()->Unit,
    onBrowseClicked:()->Unit
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
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                selectedMeal.strMeal?.let {
                    Text(text = it,
                        color = contentColor,
                        fontSize = androidx.compose.material.MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(8f))
                }
                IconButton(onClick = {
                    onBrowseClicked()
                 }) {
                    Icon(painter = painterResource(id = R.drawable.ic_youtube),
                        contentDescription = stringResource(R.string.youtube_icon),
                        modifier = Modifier.size(30.dp),
                        tint = iconColor
                    )
                }
                IconButton(onClick = { onSavedClicked() }) {
                    Icon(imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.favorite_icon),
                        modifier = Modifier.size(30.dp),
                        tint = iconColor
                    )
                }
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
        Column(modifier = Modifier
            .wrapContentSize()
            .verticalScroll(rememberScrollState())) {
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
                        .padding(bottom = MEDIUM_PADDING),
                    color = contentColor,
                    fontSize = androidx.compose.material.MaterialTheme.typography.body1.fontSize)
            }
            Column(modifier = Modifier.wrapContentSize()) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Ingredients")
                    Text(text = "Measure")
                }
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
                if (selectedMeal.strIngredient1 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient1?.let { Text(text = it) }
                        selectedMeal.strMeasure1?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient2 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient2?.let { Text(text = it) }
                        selectedMeal.strMeasure2?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient3 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient3?.let { Text(text = it) }
                        selectedMeal.strMeasure3?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient4 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient4?.let { Text(text = it) }
                        selectedMeal.strMeasure4?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient5 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient5?.let { Text(text = it) }
                        selectedMeal.strMeasure5?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient6 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient6?.let { Text(text = it) }
                        selectedMeal.strMeasure6?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient7 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient7?.let { Text(text = it) }
                        selectedMeal.strMeasure7?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient8 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient8?.let { Text(text = it) }
                        selectedMeal.strMeasure8?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient9 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient9?.let { Text(text = it) }
                        selectedMeal.strMeasure9?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient10 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient10?.let { Text(text = it) }
                        selectedMeal.strMeasure10?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient11 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient11?.let { Text(text = it) }
                        selectedMeal.strMeasure11?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient12 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient12?.let { Text(text = it) }
                        selectedMeal.strMeasure12?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient13 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient13?.let { Text(text = it) }
                        selectedMeal.strMeasure13?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient14 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient14?.let { Text(text = it) }
                        selectedMeal.strMeasure14?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient15 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient15?.let { Text(text = it) }
                        selectedMeal.strMeasure15?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient16 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient16?.let { Text(text = it) }
                        selectedMeal.strMeasure16?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient17 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient17?.let { Text(text = it) }
                        selectedMeal.strMeasure17?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient18 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient18?.let { Text(text = it) }
                        selectedMeal.strMeasure18?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient19 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient19?.let { Text(text = it) }
                        selectedMeal.strMeasure19?.let { Text(text = it) }
                    }
                }
                if (selectedMeal.strIngredient20 != "") {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        selectedMeal.strIngredient20?.let { Text(text = it) }
                        selectedMeal.strMeasure20?.let { Text(text = it) }
                    }
                }
            }
        }
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