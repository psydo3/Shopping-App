package com.example.shoppingapp.store.data.repository

import android.util.Log
import com.example.shoppingapp.store.data.remote.ProductApi
import com.example.shoppingapp.store.domain.Result
import com.example.shoppingapp.store.domain.model.Product
import com.example.shoppingapp.store.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl constructor(
    private val productApi: ProductApi
): ProductRepository {
    override suspend fun getProducts(): Flow<Result<List<Product>>> {
        return flow {
            val productsFromApi = try {
                Log.d("io", "p")
                productApi.getProducts()

            } catch (e: IOException) {
                Log.d("io", "io")
                e.printStackTrace()
                emit(Result.Error(message = "Couldn't load data"))
                return@flow

            } catch (e: HttpException) {
                Log.d("http", "http")
                e.printStackTrace()
                emit(Result.Error(message = "Couldn't load data"))
                return@flow

            } catch (e: Exception) {
                Log.d("aaa", e.toString())
                e.printStackTrace()
                emit(Result.Error(message = "Couldn't load data"))
                return@flow
            }

            emit(Result.Success(productsFromApi))
        }
    }
}