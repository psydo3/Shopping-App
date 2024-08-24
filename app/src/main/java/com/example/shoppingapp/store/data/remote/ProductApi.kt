package com.example.shoppingapp.store.data.remote

import com.example.shoppingapp.store.domain.model.Product
import retrofit2.http.GET

interface ProductApi {

    @GET("products/category/jewelery")
    suspend fun getProducts(): List<Product>
}