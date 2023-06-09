package com.rickiand.morty.utils

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.rickiand.morty.R
import com.rickiand.morty.screen.DialogScreen
import com.rickiand.morty.screen.EpisodeScreen
import com.rickiand.morty.screen.ListingScreen
import com.rickiand.morty.screen.LocationScreen
import com.rickiand.morty.ui.theme.Blue
import com.rickiand.morty.ui.theme.Pink40
import com.rickiand.morty.ui.theme.Pink80
import com.rickiand.morty.ui.theme.Purple40
import com.rickiand.morty.ui.theme.Purple80
import com.rickiand.morty.ui.theme.PurpleGrey80
import com.rickiand.morty.ui.theme.Red

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: Int) {
    object ListPerson : Screen("listing", R.string.bottom_menu_heroes, R.drawable.image_person)

    object Location : Screen("location", R.string.bottom_menu_location, R.drawable.image_location)

    object Episode : Screen("episode", R.string.bottom_menu_episode, R.drawable.image_episode)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyNavigationGraph(
    navController: NavHostController = rememberAnimatedNavController()
) {
    val item = listOf(Screen.ListPerson, Screen.Location, Screen.Episode)
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.ListPerson.route
    ) {
        composable(
            Screen.ListPerson.route,
            enterTransition = {
                slideInHorizontally { -1000 }
            },
            exitTransition = {
                slideOutHorizontally { -1000 }
            }
        ) {
            ListingScreen()
        }
        composable(
            Screen.Location.route,
            enterTransition = {
                when (initialState.destination.route) {
                    Screen.ListPerson.route -> slideInHorizontally { 1000 }
                    Screen.Episode.route -> slideInHorizontally { -1000 }
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screen.ListPerson.route -> slideOutHorizontally { 1000 }
                    Screen.Episode.route -> slideOutHorizontally { -1000 }
                    else -> null
                }
            }
        ) {
            LocationScreen()
        }
        composable(
            Screen.Episode.route,
            enterTransition = {
                slideInHorizontally { 1000 }
            },
            exitTransition = {
                slideOutHorizontally { 1000 }
            }
        ) {
            EpisodeScreen()
        }
//        composable(
//            "dialog"
//        ) {
//            DialogScreen(navController = navController)
//        }
    }
}

@Composable
fun MyBottomNavigation(
    navController: NavController
) {
    val items = listOf(
        Screen.ListPerson,
        Screen.Location,
        Screen.Episode
    )

    NavigationBar(
        modifier = Modifier
            .background(
                Brush.horizontalGradient(
                    listOf(Purple80, PurpleGrey80, Pink80),
                )
            ),
        containerColor = Color.Transparent
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val colorsItem = remember {
            mutableStateOf(Red)
        }

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.resourceId)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.resourceId),
                        fontSize = 9.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorsItem.value,
                    selectedTextColor = colorsItem.value
                ),
                selected = if (currentRoute == item.route) {
                    colorsItem.value = Blue
                    true
                } else {
                    colorsItem.value = Red
                    false
                },
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}