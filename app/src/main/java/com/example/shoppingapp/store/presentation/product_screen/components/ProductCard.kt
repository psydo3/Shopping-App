package com.example.shoppingapp.store.presentation.product_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.shoppingapp.store.domain.model.Product
import com.example.shoppingapp.store.presentation.CartEvent
import com.example.shoppingapp.store.presentation.CartState

@Composable
fun ProductCard(
    product: Product,
    onEvent: (CartEvent) -> Unit,
    state: CartState
) {
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
            .fillMaxHeight(0.7f)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (imageState is AsyncImagePainter.State.Success) {
                Image(
                    modifier = Modifier
                        .weight(3f)
                        .padding(8.dp),
                    painter = imageState.painter,
                    contentDescription = product.image,
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            Text(
                text = product.title,
                overflow = Ellipsis,
                modifier = Modifier
                    .weight(7f)
                    .align(Alignment.Top)
                    .padding(top = 8.dp, end = 6.dp)
            )
        }

        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.Black)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "£" + product.price.toString(),
                modifier = Modifier.padding(start = 16.dp)
                )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("Add to cart: ")

                CartSwitch(
                    product,
                    onEvent,
                    state,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }

    }
}

@Composable
fun CartSwitch(
    product: Product,
    onEvent: (CartEvent) -> Unit,
    state: CartState,
    modifier: Modifier = Modifier
) {
    var switchBoolean = remember { false }

    for(item in state.cartItems){
        if(item.productId == product.id){
            switchBoolean = true
        }
    }

    Switch(
        modifier = Modifier
            .padding(6.dp),
        checked = switchBoolean,
        onCheckedChange = {
            if(!switchBoolean){
                onEvent(CartEvent.Add(productId = product.id))
            } else {
                for(item in state.cartItems){
                    if(item.productId == product.id){
                        onEvent(CartEvent.Remove(item))
                    }
                }
            }

            switchBoolean = it
        }
    )
}