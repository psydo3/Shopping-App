package com.example.shoppingapp.store.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertCartItem(cartItem: Cart)

    @Delete
    suspend fun deleteCartItem(cartItem: Cart)

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<Cart>>

}