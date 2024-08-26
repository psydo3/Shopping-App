package com.example.shoppingapp.store.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val productId: Int,
)