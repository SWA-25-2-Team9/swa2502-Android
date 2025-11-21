package com.example.swa2502.domain.usecase.manage

import com.example.swa2502.domain.model.Order
import com.example.swa2502.domain.repository.ManageRepository

class GetOrdersUseCase(
    private val repository: ManageRepository
) {
    suspend fun getAllOrders(): List<Order> =
        // TODO: 이후에 repository를 통해 불러오도록 수정
        listOf(
            Order(
                id = "100",
                orderNumber = "100",
                orderState = "조리 완료",
                menus = listOf("라면"),
                timeStamp = "12:00"
            ),
            Order(
                id = "101",
                orderNumber = "101",
                orderState = "조리중",
                menus = listOf("김밥", "떡볶이"),
                timeStamp = "12:05"
            ),
            Order(
                id = "102",
                orderNumber = "102",
                orderState = "조리중",
                menus = listOf("돈까스"),
                timeStamp = "12:10"
            )
        )

    // 조리중인 주문 불러오기
    suspend fun getOrdersInProgress() = {

    }

    // 수령 대기중인 주문 불러오기
    suspend fun getPreparedOrders() = {

    }
}