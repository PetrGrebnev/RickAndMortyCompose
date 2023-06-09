package com.rickiand.morty.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rickiand.morty.screen.model.PersonUiState
import com.rickiand.morty.screen.viewmodel.MainViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsPersonContent(
    viewModel: MainViewModel = hiltViewModel(),
    id: Int
) {
    val state = remember {
        mutableStateOf(PersonUiState())
    }


    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.value.name,
                fontSize = 24.sp,
                color = Color.Black
            )

            GlideImage(
                model = state.value.image,
                contentDescription = "avatar",
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "Рик Санчез — главный герой мультсериала, сумасшедший ученый, который живет в своей лаборатории на планете Земля. Он постоянно попадает в различные приключения вместе со своим сыном Морти, который является его лучшим другом и помощником.\n" +
                    "\n" +
                    "Рик — умный и находчивый человек, который всегда находит выход из любой ситуации. Он обладает необычными способностями и знаниями, которые помогают ему решать проблемы и преодолевать трудности.\n" +
                    "\n" +
                    "Морти — молодой и любознательный мальчик, который постоянно пытается узнать что-то новое и необычное. Он часто попадает в неприятности из-за своей любознательности, но благодаря Рику он всегда находит способ выбраться из сложных ситуаций.\n" +
                    "\n" +
                    "Оба персонажа обладают чувством юмора и способностью находить выход из самых неожиданных ситуаций. Они часто используют свои знания и способности для того, чтобы исследовать вселенную и находить новые приключения.",
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}