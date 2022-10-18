package com.sribanavasi.vegitableendor.NavGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sribanavasi.vegitableendor.screen.PriceCalculator
import com.sribanavasi.vegitableendor.screen.SplashScreen
import com.sribanavasi.vegitableendor.viewmodel.MainViewModel

@Composable
fun MainNavGraph(
    navController: NavHostController,
    viewModel : MainViewModel = hiltViewModel()
){
    NavHost(navController = navController, startDestination = Screen.splash.route) {
        composable(Screen.splash.route){
            SplashScreen(navController=navController,viewModel=viewModel)
        }
        composable(Screen.priceCalculator.route){
            PriceCalculator(navController=navController,viewModel=viewModel)
        }
    }
}