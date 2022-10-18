package com.sribanavasi.vegitableendor.NavGraph

sealed class Screen(val route: String){
    object splash : Screen(route = "splash")
    object priceCalculator : Screen(route = "price_calculator")
}
