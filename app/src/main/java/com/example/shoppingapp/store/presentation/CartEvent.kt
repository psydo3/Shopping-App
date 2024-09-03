package com.example.shoppingapp.store.presentation

import com.example.shoppingapp.store.data.local.Cart

sealed interface CartEvent {

    data class Add (val productId: Int) : CartEvent

    data class Remove(val cartItem: Cart) : CartEvent
}