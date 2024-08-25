package com.example.shoppingapp.store.domain.repository

import com.example.shoppingapp.store.domain.Result
import com.example.shoppingapp.store.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Result<List<Product>>>

}