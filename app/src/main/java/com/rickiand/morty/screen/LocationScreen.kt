@file:OptIn(ExperimentalGlideComposeApi::class)

package com.rickiand.morty.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rickiand.morty.R
import com.rickiand.morty.data.model.DetailPersonDto
import com.rickiand.morty.domain.model.LocationDomain
import com.rickiand.morty.screen.model.LocationUiState
import com.rickiand.morty.screen.utils.ShowLoadingIndicator
import com.rickiand.morty.screen.viewmodel.LocationViewModel
import com.rickiand.morty.ui.theme.Black
import com.rickiand.morty.ui.theme.Red

@Composable
fun LocationScreen() {
    val viewModel = hiltViewModel<LocationViewModel>()
    val state = remember {
        mutableStateOf(LocationUiState())
    }
    state.value = viewModel.uiState.collectAsState().value
    viewModel.getLocation()


    val sizeState = remember {
        mutableStateOf(IntSize(0, 0))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .onGloballyPositioned {
                sizeState.value = it.size
            }
    ) {
        if (state.value.loading) {
            ShowLoadingIndicator(size = sizeState.value)
        } else {
            ShowListLocation(state)
        }
    }
}

@Composable
private fun ShowListLocation(
    state: MutableState<LocationUiState>
) {
    LazyColumn(
    ) {
        items(state.value.list) {
            CardLocation(
                modifier = Modifier.padding(16.dp),
                location = it
            ) {
                ListPersonLocation(it.imagePerson)
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
private fun CardLocation(
    modifier: Modifier,
    location: LocationDomain,
    content: @Composable () -> Unit
) {
    Card() {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            ShowText(
                textOne = "Name: ",
                textTwo = location.name
            )
            ShowText(
                textOne = "Type: ",
                textTwo = location.type
            )
            ShowText(
                textOne = "Dimension: ",
                textTwo = location.dimension
            )
            ShowText(textOne = "Recently Noticed Persons: ")
            content()
        }
    }
}

@Composable
private fun ShowText(
    textOne: String = "",
    textTwo: String = "",
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Red
                )
            ) {
                append(textOne)
            }
            withStyle(
                style = SpanStyle(
                    color = Black
                )
            ) {
                append(textTwo)
            }
        },
        fontSize = 20.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
private fun ListPersonLocation(
    list: List<DetailPersonDto>
) {
    if (list.isNotEmpty()) {
        LazyRow {
            item {
                list.forEach {
                    CardPerson(person = it)
                }
            }
        }
    } else {
        ShowText(textTwo = "It seems there was no one")
    }
}

@Composable
private fun CardPerson(
    modifier: Modifier = Modifier,
    person: DetailPersonDto
) {
    Column(
        modifier = modifier
            .padding(2.dp)
            .width(100.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            GlideImage(
                model = person.image,
                contentDescription = "avatar"
            ) {
                it.placeholder(R.drawable.placeholder)
            }
        }
        Text(
            modifier = Modifier,
            text = person.name,
            maxLines = 1,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            color = Color.Blue
        )
    }
}