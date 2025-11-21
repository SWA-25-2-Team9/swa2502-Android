// MyOrderViewModel.kt

package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// ----------------------------------------------------
// 1. API 응답 데이터 모델 정의
// ----------------------------------------------------
data class MyOrderInfo(
    val orderId: Int,
    val orderNumber: String, // 주문 번호 (예: 'A-123')
    val storeName: String,
    val orderStatus: String, // 주문 상태 (예: "주문 접수", "메뉴 준비 중", "픽업 가능")
    val estimatedWaitTime: String, // 예상 대기 시간 (예: "5분 예상")
    val myTurn: Int, // 내 대기 순번
    val totalPeopleInQueue: Int // 전체 대기 인원
)

// ----------------------------------------------------
// 2. UI 상태 정의
// ----------------------------------------------------
data class MyOrderUiState(
    val isLoading: Boolean = false,
    val orderInfo: MyOrderInfo? = null, // 주문 정보
    val errorMessage: String? = null,
)

@HiltViewModel
class MyOrderViewModel @Inject constructor(
    // private val orderRepository: OrderRepository // 실제 Repository 필요
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyOrderUiState())
    val uiState: StateFlow<MyOrderUiState> = _uiState.asStateFlow()

    init {
        // 더미 데이터 로드
        loadMyOrderInfo()
    }

    private fun loadMyOrderInfo() {
        // 실제 API 호출 로직 대신 더미 데이터 사용
        val dummyOrderInfo = MyOrderInfo(
            orderId = 1,
            orderNumber = "111번",
            storeName = "가게 이름",
            orderStatus = "주문 접수",
            estimatedWaitTime = "",
            myTurn = 11,
            totalPeopleInQueue = 15
        )
        _uiState.update {
            it.copy(orderInfo = dummyOrderInfo)
        }
    }
}