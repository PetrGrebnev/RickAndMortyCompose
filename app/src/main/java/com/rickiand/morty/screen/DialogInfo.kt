package com.rickiand.morty.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rickiand.morty.screen.viewmodel.MainViewModel

@Composable
fun DialogScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val dialogState = remember {
        mutableStateOf(true)
    }

    if (dialogState.value) {
        Dialog(
            onDismissRequest = { dialogState.value = false },
            content = {
                CompleteDialogContent(title = "Title", dialogState = dialogState, successButtonText = "OK") {
                    BodyContent()
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    } else {
        navController.navigateUp()
        Toast.makeText(context, "Dialog Closed", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun BodyContent() {
    Text(
        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type " +
                "specimen book. It has survived not only five centuries, but also the leap into " +
                "electronic typesetting, remaining essentially unchanged. It was popularised in " +
                "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and " +
                "more recently with desktop publishing software like Aldus PageMaker including " +
                "versions of Lorem Ipsum.",
        fontSize = 22.sp
    )
}

@Composable
fun CompleteDialogContent(
    title: String,
    dialogState: MutableState<Boolean>,
    successButtonText: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                TitleAndButton(title = title, dialogState = dialogState)
                AddBody(content)
                BottomButtons(successButtonText = successButtonText, dialogState = dialogState)
            }
        }
    }
}

@Composable
private fun TitleAndButton(
    title: String,
    dialogState: MutableState<Boolean>
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 24.sp
            )
            IconButton(
                onClick = {
                dialogState.value = false
                }
            ) {
                Icon(Icons.Filled.Close, "describe")
            }
        }
        Divider(
            color = Color.Black,
            thickness = 1.dp
        )
    }
}

@Composable
private fun AddBody(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(20.dp)) {
        content()
    }
}

@Composable
private fun BottomButtons(
    successButtonText: String,
    dialogState: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxWidth(1f)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                dialogState.value = false
            },
            modifier = Modifier.width(100.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = successButtonText, fontSize = 20.sp)
        }
    }
}