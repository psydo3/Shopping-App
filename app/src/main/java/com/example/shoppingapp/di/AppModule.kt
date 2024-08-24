package com.example.shoppingapp.di

import com.example.shoppingapp.store.data.remote.ProductApi
import com.example.shoppingapp.store.data.repository.ProductRepositoryImpl
import com.example.shoppingapp.store.domain.repository.ProductRepository
import com.example.shoppingapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): ProductApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: ProductApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }
}