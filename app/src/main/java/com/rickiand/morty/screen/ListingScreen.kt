@file:OptIn(ExperimentalMaterial3Api::class)

package com.rickiand.morty.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rickiand.morty.R
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.screen.utils.ContentBottomSheet
import com.rickiand.morty.screen.utils.MyBottomSheet
import com.rickiand.morty.screen.utils.ShowLoadingIndicator
import com.rickiand.morty.screen.viewmodel.MainViewModel
import com.rickiand.morty.ui.theme.Black
import com.rickiand.morty.ui.theme.Blue
import com.rickiand.morty.ui.theme.Green
import com.rickiand.morty.ui.theme.Red
import com.rickiand.morty.ui.theme.blue2
import com.rickiand.morty.ui.theme.pink1
import com.rickiand.morty.ui.theme.pink2

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListingScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val state = mainViewModel.getList().collectAsLazyPagingItems()

    val personState =
        mainViewModel.uiStateDetail.collectAsState()

    val personIdState = remember {
        mutableStateOf("")
    }
    val sheetState = remember {
        mutableStateOf(false)
    }
    val listState = remember {
        mutableStateOf(LazyListState())
    }

    val bottomState = rememberModalBottomSheetState()
    if (personIdState.value.isNotEmpty()) {
        mainViewModel.getPerson(personIdState.value)
    }

    val size = remember {
        mutableStateOf(IntSize(0, 0))
    }

    if (sheetState.value) {
        MyBottomSheet(
            bottomSheetState = sheetState,
            state = bottomState
        ) {
            if (personState.value.loading) {
                ShowLoadingIndicator(size.value)
            } else {
                ContentBottomSheet(person = personState.value.person)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                size.value = it.size
            }
    ) {
        if (state.loadState.refresh == LoadState.Loading) {
            ShowLoadingIndicator(size = size.value)
        } else {
            ListPerson(
                state = state,
                bottomSheetState = sheetState,
                personId = personIdState,
                scrollState = listState.value
            )
        }
    }
}


@Composable
private fun ListPerson(
    state: LazyPagingItems<PersonDto>,
    bottomSheetState: MutableState<Boolean>,
    personId: MutableState<String>,
    scrollState: LazyListState
) {
    var oldFirstItems: Int? = null
    val animState = remember {
        mutableStateOf(false)
    }

    val animation = remember {
        Animatable(initialValue = 1f)
    }


    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp),
        contentPadding = PaddingValues(0.dp),
        state = scrollState
    ) {
        items(
            items = state,
            key = { item: PersonDto -> item.id }
        ) { person ->
            if (person != null) {
                CartPerson(
                    modifier = Modifier
                        .graphicsLayer(scaleY = animation.value),
                    person = person,
                    bottomSheetState = bottomSheetState,
                    personId = personId,
                    scrollState = scrollState
                )
                animState.value = person.id == scrollState.firstVisibleItemIndex

                LaunchedEffect(key1 = animState) {
                    if (animState.value) {
                        animation.animateTo(
                            targetValue = 1f / scrollState.firstVisibleItemScrollOffset,
                            animationSpec = tween(1000)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun CartPerson(
    modifier: Modifier,
    person: PersonDto,
    bottomSheetState: MutableState<Boolean>,
    personId: MutableState<String>,
    scrollState: LazyListState
) {

    val borderState = remember {
        mutableStateOf(
            BorderStroke(
                width = 2.dp,
                brush = Brush.horizontalGradient(listOf(pink1, pink2, blue2))
            )
        )
    }
    if (!bottomSheetState.value) {
        borderState.value = BorderStroke(
            width = 2.dp,
            brush = Brush.horizontalGradient(listOf(pink1, pink2, blue2))
        )
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(Color.Transparent),
        onClick = {
            borderState.value = BorderStroke(
                width = 4.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(Red, Green, Blue),
                    tileMode = TileMode.Repeated
                )
            )
            personId.value = person.url
            bottomSheetState.value = true
        },
        border = borderState.value
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(4.dp)
            ) {
                GlideImage(
                    model = person.image,
                    contentDescription = "image",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit
                ) {
                    it.placeholder(R.drawable.placeholder)
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 16.dp),
            ) {
                Text(
                    text = person.name,
                    fontSize = 24.sp,
                    color = Black
                )

                Text(
                    text = "Gender: ${person.gender}",
                    fontSize = 14.sp,
                    color = Black,
                    modifier = Modifier.padding(top = 2.dp)
                )

                if (person.status == "Dead") {
                    Image(
                        painter = painterResource(id = R.drawable.image_rip),
                        contentDescription = "RIP",
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .padding(top = 6.dp)
                    )
                }
            }
        }
    }
}


