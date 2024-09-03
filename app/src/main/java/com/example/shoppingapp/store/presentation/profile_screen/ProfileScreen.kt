package com.example.shoppingapp.store.presentation.profile_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.store.presentation.view_models.CartViewModel
import com.example.shoppingapp.store.presentation.view_models.ProductViewModel
import com.example.shoppingapp.store.presentation.profile_screen.components.CartProduct

@Composable
fun ProfileScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    val productList = productViewModel.products.collectAsState().value
    val cartState by cartViewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.91f),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(productList.size) {
            for(item in cartState.cartItems){
                if (item.productId == productList[it].id){
                    CartProduct(
                        product = productList[it],
                        cartViewModel::onEvent,
                        cartState
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}