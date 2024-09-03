package com.example.shoppingapp.store.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.store.data.local.Cart
import com.example.shoppingapp.store.data.local.CartDatabase
import com.example.shoppingapp.store.presentation.CartEvent
import com.example.shoppingapp.store.presentation.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CartViewModel @Inject constructor(
    @Named("cartDatabase") private val cartDatabase: CartDatabase
): ViewModel() {
    private val dao = cartDatabase.dao

    private val _state = MutableStateFlow(CartState())

    val state = combine(_state, dao.getAllCartItems()) { state, items ->
        state.copy(
            cartItems = items
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CartState())

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.Remove -> {
                viewModelScope.launch {
                    dao.deleteCartItem(event.cartItem)
                }
            }

            is CartEvent.Add -> {
                viewModelScope.launch {
                    val cartItem = Cart(productId = event.productId)

                    dao.insertCartItem(cartItem)
                }
            }
        }
    }
}