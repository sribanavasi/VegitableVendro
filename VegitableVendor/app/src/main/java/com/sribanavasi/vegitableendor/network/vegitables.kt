package com.sribanavasi.vegitableendor.network

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class Vegitables(
    @SerializedName("items" ) var items : ArrayList<Items> = arrayListOf()
)

data class Items (

    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("price" ) var price : Int?    = null,
    @SerializedName("link" ) var link : String? = null

)

class VegetablePrice(
    var vegetable :Items? = null,
    var quantity : Double = 0.0,
){
    val amount: Double? get() = DecimalFormat("#.##").format(this.vegetable?.price?.times(this.quantity)).toDouble()
}
