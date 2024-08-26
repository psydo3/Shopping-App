package com.example.shoppingapp.store.presentation.product_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.store.presentation.CartViewModel
import com.example.shoppingapp.store.presentation.ProductViewModel
import com.example.shoppingapp.store.presentation.product_screen.components.ProductCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductScreen() {

    val viewModel = hiltViewModel<ProductViewModel>()
    val productList = viewModel.product.collectAsState().value

    val cartViewModel = hiltViewModel<CartViewModel>()
    val cartState by cartViewModel.state.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (productList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.91f),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(productList.size) {
                ProductCard(
                    product = productList[it],
                    cartViewModel::onEvent,
                    cartState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}