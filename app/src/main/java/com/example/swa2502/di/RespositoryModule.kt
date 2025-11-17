package com.example.swa2502.di

import com.example.swa2502.data.repository.OrderRepositoryImpl
import com.example.swa2502.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * OrderRepository 인터페이스 요청 시, OrderRepositoryImpl 구현체를 제공하도록 바인딩
     */
    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}