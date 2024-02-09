package com.example.myrecipeapp.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myrecipeapp.R
import com.example.myrecipeapp.util.Dimens
import com.example.myrecipeapp.util.Dimens.ExtraSmallPadding2
import com.example.myrecipeapp.util.Dimens.IconSize
import com.example.myrecipeapp.util.Dimens.MediumPadding1
import com.example.myrecipeapp.util.Dimens.MediumPadding2

@Composable
fun RecipeBottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClicked:(Int)->Unit) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 10.dp
    ) {
        items.forEachIndexed{ index, item->
            BottomNavigationItem(
                selected = index == selected,
                onClick = { onItemClicked(index) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = item.icon,
                            contentDescription = "",
                            modifier = Modifier.size(IconSize))
                        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                    }
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black
            )
        }
    }
}

data class BottomNavigationItem(
    val icon: ImageVector,
    val text: String
)

fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
    background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
}

@Composable
fun CardShimmer() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(MediumPadding1),
        horizontalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(4) {
            Card(
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .padding(Dimens.SmallPadding)
                            .size(width = 100.dp, height = 100.dp)
                            .shimmerEffect(),
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = Dimens.SmallPadding)
                            .size(width = 100.dp, height = 10.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}

@Composable
fun BigCardShimmer() {
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        contentPadding = PaddingValues(Dimens.MediumPadding2),
        columns = GridCells.Fixed(2)){
        items(10) {
            Card(
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .padding(MediumPadding2)
                            .size(width = 150.dp, height = 130.dp)
                            .shimmerEffect(),
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = MediumPadding2)
                            .fillMaxWidth()
                            .height(30.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}

@Composable
fun SingleCardShimmer() {
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        contentPadding = PaddingValues(Dimens.MediumPadding2),
        columns = GridCells.Fixed(1)){
        items(3) {
            Card(
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .shimmerEffect(),
                    )
                    Box(
                        modifier = Modifier
                            .padding(MediumPadding2)
                            .fillMaxWidth()
                            .height(10.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}