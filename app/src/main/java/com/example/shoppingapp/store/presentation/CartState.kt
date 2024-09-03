package com.example.shoppingapp.store.presentation

import com.example.shoppingapp.store.data.local.Cart

data class CartState (
    val cartItems: List<Cart> = emptyList(),
)