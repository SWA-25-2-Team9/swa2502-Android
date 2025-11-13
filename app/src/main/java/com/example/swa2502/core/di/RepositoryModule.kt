package com.example.swa2502.core.di

import com.example.swa2502.data.repository.AuthRepositoryImpl
import com.example.swa2502.data.repository.FakePayRepositoryImpl
import com.example.swa2502.data.repository.ManageRepositoryImpl
import com.example.swa2502.data.repository.OrderRepositoryImpl
import com.example.swa2502.data.repository.QueueRepositoryImpl
import com.example.swa2502.data.repository.RestaurantRepositoryImpl
import com.example.swa2502.domain.repository.AuthRepository
import com.example.swa2502.domain.repository.ManageRepository
import com.example.swa2502.domain.repository.OrderRepository
import com.example.swa2502.domain.repository.PayRepository
import com.example.swa2502.domain.repository.QueueRepository
import com.example.swa2502.domain.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindManageRepository(
        impl: ManageRepositoryImpl
    ): ManageRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    abstract fun bindQueueRepository(
        impl: QueueRepositoryImpl
    ): QueueRepository

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl
    ): RestaurantRepository

    @Binds
    @Singleton
    abstract fun bindPayRepository(
        impl: FakePayRepositoryImpl
    ): PayRepository
}