package com.example.shoppingapp.store.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 2, exportSchema = false)
abstract class CartDatabase: RoomDatabase() {
    abstract val dao: CartDao
}