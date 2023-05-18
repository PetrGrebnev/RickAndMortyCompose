package com.rickiand.morty.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rickiand.morty.screen.ListingScreen

//@Composable
//fun NavHost(
//    modifier: Modifier = Modifier,
//    navController: NavController = rememberNavController(),
//    startDestination: String = "lisitng"
//) {
//    NavHost(
//        modifier = modifier,
//        navController = navController,
//        startDestination = startDestination
//    ) {
//        composable("listing") {
//            ListingScreen(
//                onNavigateDetails = { navController.navigate("details")}
//            )
//        }
//    }
//}