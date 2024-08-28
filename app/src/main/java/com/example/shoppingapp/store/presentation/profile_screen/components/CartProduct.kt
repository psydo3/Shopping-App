package com.example.shoppingapp.store.presentation.profile_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun CartProduct(
    product: Product,
    onEvent: (CartEvent) -> Unit,
    state: CartState
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.image)
            .size(Size.ORIGINAL).build()
    ).state

    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
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
            maxLines = 1,
            overflow = Ellipsis,
            modifier = Modifier.weight(7f)
        )

        IconButton(
            onClick = {
                for(item in state.cartItems){
                    if(item.productId == product.id){
                        onEvent(CartEvent.Remove(item))
                    }
                }
            },
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}