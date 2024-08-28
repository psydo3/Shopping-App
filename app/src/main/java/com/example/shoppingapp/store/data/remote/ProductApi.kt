package com.example.shoppingapp.store.data.remote

import com.example.shoppingapp.store.domain.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): List<Product>
}