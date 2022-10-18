package com.sribanavasi.vegitableendor.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.sribanavasi.vegitableendor.network.Items
import com.sribanavasi.vegitableendor.network.VegetablePrice
import com.sribanavasi.vegitableendor.viewmodel.MainViewModel

@Composable
fun PriceCalculator(navController: NavHostController, viewModel: MainViewModel) {
    val vegetables by viewModel.vegetables.observeAsState()
    val selectedVegetable by viewModel.selectedVegetable.observeAsState()
    val onSelect by viewModel.onSelect.observeAsState()
    val quentity by viewModel.quentity.observeAsState("")

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight()
        ) {
            vegetables?.let { vegetableGrid(vegetables = it.items, onclick = {viewModel.select(VegetablePrice(vegetable=it))}) }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            bill(selectedVegetable,onSelect,quentity,viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun vegetableGrid(vegetables: List<Items>,onclick:(data:Items)->Unit) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(vegetables.size) { index ->
            var size by remember { mutableStateOf(IntSize.Zero) }
            val link = vegetables[index].link.toString()
            val startIndex = link.indexOf("d/")
            val id = link.subSequence(startIndex+2,link.indexOf("/",startIndex+2))
            Log.d("id","$id")
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .onSizeChanged {
                        size = it
                    }
                    .clickable { onclick(vegetables[index]) },
                elevation = 6.dp,
            ) {
                Box(modifier = Modifier.then(with(LocalDensity.current) {
                    Modifier.height(size.width.toDp())
                })) {
                    Image(
                        painter = rememberImagePainter("https://drive.google.com/uc?export=view&id=$id"),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Black.copy(alpha = 0.7f))
                            .align(
                                Alignment.BottomCenter
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (vegetables[index].name != null) vegetables[index].name!! else "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(24.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun bill(selectedVegetable: List<VegetablePrice>?,onSelect:VegetablePrice?,quentity:String,viewModel: MainViewModel){
    LazyColumn(){
        var totalAmount:Double = 0.0
        selectedVegetable?.let {
            items(it.size){ i ->
                valueCard(it[i])
                 it[i].amount?.let{totalAmount+=it}
            }
        }
        onSelect?.let {
            item{
                enterCard(it, quentity,onValueChange = {viewModel.updateValue(it) })
            }
            it.amount?.let { totalAmount+=it }
        }
        item{
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), color = Color.Black, thickness = 2.dp)
        }
        item{
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black)
                Text("${totalAmount} Rs",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Start,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black)
            }
        }
    }
}

@Composable
fun enterCard(data:VegetablePrice,quentity:String,onValueChange: (data: String) -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text("${data.vegetable?.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black)
        Row(modifier = Modifier
            .fillMaxWidth(),
        ) {
            TextField(value= quentity,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = Color.Black,
                    textAlign = TextAlign.End,
                    fontSize = 20.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Text("*",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black)
            Text("${data.amount}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black)
        }
    }
}

@Composable
fun valueCard(data:VegetablePrice){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Text("${data.vegetable?.name}",
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black)
        Row(modifier = Modifier
            .fillMaxWidth(),
        ) {
            Text("${data.quantity}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(0.3f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black)
            Text("*${data.vegetable?.price} Rs =",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black)
            Text(" ${data.amount} Rs",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black)
        }
    }
}