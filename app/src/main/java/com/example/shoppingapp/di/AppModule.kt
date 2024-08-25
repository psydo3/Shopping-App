package com.example.shoppingapp.di

import android.app.Application
import androidx.room.Room
import com.example.shoppingapp.store.data.local.CartDatabase
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
import javax.inject.Named
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
    @Named("productRepository")
    fun provideRepository(api: ProductApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    @Named("cartDatabase")
    fun provideDatabase(
        app: Application,
    ): CartDatabase {
        return Room.databaseBuilder(
            app,
            CartDatabase::class.java,
            "cart.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}