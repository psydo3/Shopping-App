package com.example.shoppingapp.store.presentation.product_screen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.shoppingapp.store.data.local.Cart
import com.example.shoppingapp.store.domain.model.Product
import com.example.shoppingapp.store.domain.repository.CartEvent
import com.example.shoppingapp.store.presentation.CartViewModel

@Composable
fun ProductCard(product: Product) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.image)
            .size(Size.ORIGINAL).build()
    ).state

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(150.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .height(150.dp),
                painter = imageState.painter,
                contentDescription = product.image,
                contentScale = ContentScale.Fit
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
        ){
            Text(
                text = product.title,
                overflow = Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )

            CartSwitch(product)
        }
    }
}

@Composable
fun CartSwitch(
    product: Product
) {
    val cartViewModel = hiltViewModel<CartViewModel>()
    val state by cartViewModel.state.collectAsState()

    var switchBoolean = remember { false }

    for(item in state.cartItems){
        if(item.productId == product.id){
            switchBoolean = true
        }
    }

    Switch(
        checked = switchBoolean,
        onCheckedChange = {
            if(!switchBoolean){
                cartViewModel.onEvent(CartEvent.Add(productId = product.id))
            } else {
                for(item in state.cartItems){
                    if(item.productId == product.id){
                        cartViewModel.onEvent(CartEvent.Remove(item))
                    }
                }
            }

            switchBoolean = it
        }
    )
}