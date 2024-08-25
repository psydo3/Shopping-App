package com.example.shoppingapp.store.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
    @PrimaryKey
    val productId: Int,
)