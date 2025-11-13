package com.example.swa2502.core.di

import com.example.swa2502.data.api.AuthApi
import com.example.swa2502.data.api.ManageApi
import com.example.swa2502.data.api.OrderApi
import com.example.swa2502.data.api.QueueApi
import com.example.swa2502.data.api.RestaurantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun providesManageApi(retrofit: Retrofit): ManageApi =
        retrofit.create(ManageApi::class.java)

    @Provides
    @Singleton
    fun providesOrderApi(retrofit: Retrofit): OrderApi =
        retrofit.create(OrderApi::class.java)

    @Provides
    @Singleton
    fun providesQueueApi(retrofit: Retrofit): QueueApi =
        retrofit.create(QueueApi::class.java)

    @Provides
    @Singleton
    fun providesRestaurantApi(retrofit: Retrofit): RestaurantApi =
        retrofit.create(RestaurantApi::class.java)

}