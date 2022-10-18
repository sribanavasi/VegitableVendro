package com.sribanavasi.vegitableendor.repository

import android.util.Log
import com.sribanavasi.vegitableendor.network.RetrofitInterface
import com.sribanavasi.vegitableendor.network.Vegitables
import org.json.JSONObject
import javax.inject.Inject

interface NetworkRepository {
    suspend fun addVegitable(name:String,price:String,link:String):String
    suspend fun updateVegitable(name:String,price:String,link:String):String
    suspend fun deleteVegitable(name:String):String
    suspend fun getAll(): Vegitables
}

class NetworkRepositoryImp @Inject constructor(
    private val retrofitInstance: RetrofitInterface
):NetworkRepository{
    override suspend fun addVegitable(name: String, price: String, link: String): String {
        try{
            retrofitInstance.addVegitable(name,price,link).let{
                Log.d("addVegitable", it)
                return it
            }
        }catch (e:Exception){
            Log.d("addVegitable E", e.message.toString())
            return "Error"
        }
    }

    override suspend fun updateVegitable(name: String, price: String, link: String): String {
        try{
            retrofitInstance.updateVegitable(name,price,link).let{
                Log.d("updateVegitable", it)
                return it
            }
        }catch (e:Exception){
            Log.d("updateVegitable E", e.message.toString())
            return "Error"
        }
    }

    override suspend fun deleteVegitable(name: String): String {
        try{
            retrofitInstance.deleteVegitable(name).let{
                Log.d("deleteVegitable", it)
                return it
            }
        }catch (e:Exception){
            Log.d("deleteVegitable E", e.message.toString())
            return "Error"
        }
    }

    override suspend fun getAll(): Vegitables {
        try{
            retrofitInstance.getAll().let{
                Log.d("getAll", it.toString())
                return it
            }
        }catch (e:Exception){
            Log.d("getAll E", e.message.toString())
            return Vegitables()
        }
    }

}