package com.rickiand.morty.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rickiand.morty.screen.model.PersonUiState
import com.rickiand.morty.screen.model.PersonsUiState

@Composable
fun ListingScreen(
    onNavigateDetails : (() -> Unit)? = null
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val state = remember {
        mutableStateOf(PersonsUiState())
    }
    mainViewModel.getList()
    state.value = mainViewModel.uiState.collectAsState().value
    ListPerson(modifier = Modifier.fillMaxSize(), state.value.persons)
    Text(text = "start")
}

@Composable
fun ListPerson(modifier: Modifier = Modifier, list: List<PersonUiState>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            list.forEach {
                CartPerson(person = it)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartPerson(person: PersonUiState) {
    ElevatedCard(
        shape = RectangleShape
    ) {
        Row {
            Card(
                shape = MaterialTheme.shapes.small,
            ) {
                GlideImage(
                    model = person.image,
                    contentDescription = "image",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.None,
                    modifier = Modifier.apply {
                        width(100.dp)
                        height(100.dp)
                    }
                )
            }
            Column {
                Text(
                    text = person.name,
                    fontSize = 16.sp
                )

                Text(
                    text = person.gender,
                    fontSize = 14.sp
                )
            }
        }
    }
}