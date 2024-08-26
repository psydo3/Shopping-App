package com.example.shoppingapp.store.presentation

import com.example.shoppingapp.store.domain.model.Cart

data class CartState (
    val cartItems: List<Cart> = emptyList(),
)