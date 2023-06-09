package com.rickiand.morty.screen.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rickiand.morty.screen.model.PersonUiState
import com.rickiand.morty.ui.theme.Purple40
import com.rickiand.morty.ui.theme.pink2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheet(
    bottomSheetState: MutableState<Boolean>,
    state: SheetState,
    content: @Composable () -> Unit
) {

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxHeight(0.5f),
        containerColor = pink2,
        sheetState = state,
        shape = RoundedCornerShape(topStart = 16.dp),
        onDismissRequest = {
            bottomSheetState.value = false
        }
    ) {
        content()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ContentBottomSheet(
    person: PersonUiState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = person.name,
            fontSize = 24.sp,
            color = Color.Black
        )
        GlideImage(
            model = person.image,
            contentDescription = "avatar"
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Знаешь, что я думаю о школе, Джери? Это пустая трата времени. Кучка людей" +
                    " бегает туда-сюда, натыкается друг на друга, какой-то чувак стоит впереди " +
                    "и говорит: «Два плюс два...» и люди сзади говорят: «Четыре». А потом звенит" +
                    " звонок, тебе дают пакет молока и бумажку, на которой написано, что ты можешь" +
                    " пойти посрать или типа того. Я к тому, что это не место для умных людей, Джери.",
            fontSize = 14.sp,
            color = Purple40
        )
    }
}