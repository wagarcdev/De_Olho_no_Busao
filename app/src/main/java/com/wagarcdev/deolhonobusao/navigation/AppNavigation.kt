package com.wagarcdev.deolhonobusao.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wagarcdev.deolhonobusao.MainViewModel
import com.wagarcdev.deolhonobusao.presentention.screens.MapScreen

@Composable
fun AppNavigation() {

    val context = LocalContext.current

    val mainViewModel: MainViewModel = hiltViewModel()

    mainViewModel.navHostController = rememberNavController()

    NavHost(
        startDestination = Screens.MapScreen.name,
        navController = mainViewModel.navHostController
    ) {
        /** MapScreen */
        composable(Screens.MapScreen.name){
            MapScreen()
        }
    }

}


