package com.sribanavasi.vegitableendor.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sribanavasi.vegitableendor.NavGraph.Screen
import com.sribanavasi.vegitableendor.R
import com.sribanavasi.vegitableendor.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, viewModel: MainViewModel){

    LaunchedEffect(key1 = true) {
        delay(3000)
        viewModel.getAllVegetables(callBack = {
            Log.d("callbaclk","")
            navController.popBackStack()
            navController.navigate(Screen.priceCalculator.route)
        })
    }
    SplashBody()
}

@Composable
fun SplashBody(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.purple_500)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.vegitables_icon), contentDescription ="vegetables",
            modifier = Modifier.size(200.dp))
    }
}