package com.rickiand.morty.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.rickiand.morty.ui.theme.Pink40
import com.rickiand.morty.ui.theme.Purple40
import com.rickiand.morty.ui.theme.Purple80
import com.rickiand.morty.ui.theme.RickiAndMortyTheme
import com.rickiand.morty.utils.MyBottomNavigation
import com.rickiand.morty.utils.MyNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickiAndMortyTheme {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = { MyBottomNavigation(navController = navController) }
    ) {
        Box(
            modifier = Modifier.padding(it)
                .background(
                    Brush.verticalGradient(
                        listOf(Purple40, Purple80, Pink40),
                    )
                )
        ) {
            MyNavigationGraph(navController = navController)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickiAndMortyTheme {
        Greeting("Android")
    }
}