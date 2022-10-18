package com.sribanavasi.vegitableendor.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sribanavasi.vegitableendor.network.VegetablePrice
import com.sribanavasi.vegitableendor.network.Vegitables
import com.sribanavasi.vegitableendor.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: NetworkRepository
) : ViewModel() {
    private  val format = DecimalFormat("#.#")

    private val _vegetables: MutableLiveData<Vegitables> = MutableLiveData()
    var vegetables: LiveData<Vegitables> = _vegetables

    private val _selectedVegetable : MutableLiveData<List<VegetablePrice>> = MutableLiveData()
    var selectedVegetable: LiveData<List<VegetablePrice>> = _selectedVegetable

    private val _onSelect : MutableLiveData<VegetablePrice> = MutableLiveData()
    var onSelect: LiveData<VegetablePrice> = _onSelect

    private val _quentity : MutableLiveData<String> = MutableLiveData()
    var quentity: LiveData<String> = _quentity

    suspend fun getAllVegetables(callBack:()->Unit) {
        val res = repo.getAll()
        Log.d("res", "$res")
        _vegetables.value = res
        if(res.items.size>0)  callBack()
    }

    fun updateValue(que:String) {
        Log.d("onClick","updateValue ${que.toDoubleOrNull()}")
         _onSelect?.let {
             if(que.toDoubleOrNull() != null) {
                 var data = _onSelect.value!!
                 data.quantity = que.toDouble()
                 _onSelect.value = data
                 _quentity.value = que
             }
         }
    }

    fun select(data:VegetablePrice){
        Log.d("onClick","select")
        _onSelect.value?.let {
            _selectedVegetable.value = if(_selectedVegetable.value != null) _selectedVegetable.value!!.plus(it) else listOf(it)
        }

        _onSelect.value = data
        _quentity.value = ""
        viewModelScope.launch {
            delay(3000)
            updateValue(format.format(Random.nextDouble(1.0,10.0)).toString())
        }
    }

}