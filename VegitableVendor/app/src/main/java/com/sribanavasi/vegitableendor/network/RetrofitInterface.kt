package com.sribanavasi.vegitableendor.network

import org.json.JSONObject
import retrofit2.http.Field
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("exec?action=add_vegitable")
    suspend fun addVegitable(
        @Field("name") name:String ,
        @Field("price") price:String,
        @Field("link") link:String,
    ):String

    @POST("exec?action=update_vegitable")
    suspend fun updateVegitable(
        @Field("name") name:String ,
        @Field("price") price:String,
        @Field("link") link:String,
    ):String

    @POST("exec?action=delete_vegitable")
    suspend fun deleteVegitable(
        @Field("name") name:String ,
    ):String

    @POST("exec?action=get_all")
    suspend fun getAll():Vegitables
}