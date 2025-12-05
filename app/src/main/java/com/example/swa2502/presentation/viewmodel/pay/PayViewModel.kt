package com.example.swa2502.presentation.viewmodel.pay

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.update
import com.example.swa2502.domain.usecase.order.CreateOrderUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

enum class PaymentMethod {
    CARD,
    SIMPLE
}
data class PayUiState(
    val isLoading: Boolean = false,
    // 필요에 따라 상태 관리에 필요한 변수들 추가
    val errorMessage: String? = null,
    val totalPrice: Int = 0, // 총 결제 금액 (원)
    val selectedPaymentMethod: PaymentMethod = PaymentMethod.CARD, // 기본값: 신용/체크카드
)

@HiltViewModel
class PayViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PayUiState())
    val uiState: StateFlow<PayUiState> = _uiState.asStateFlow()

    init {
        // 더미 데이터 로드 (이미지의 10,000원)
        loadPaymentInfo()
    }

    private fun loadPaymentInfo() {
        _uiState.update {
            it.copy(totalPrice = 10000)
        }
    }

    fun selectPaymentMethod(method: PaymentMethod) {
        _uiState.update {
            it.copy(selectedPaymentMethod = method)
        }
    }

    fun createOrder() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val methodString = when (_uiState.value.selectedPaymentMethod) {
                PaymentMethod.CARD -> "CARD"
                PaymentMethod.SIMPLE -> "SIMPLE"
            }

            // 실제 결제 로직 대신 주문 생성 API 호출
            createOrderUseCase(methodString)
                .onSuccess {
                    // 결제 성공 및 주문 생성 완료
                    _uiState.update {
                        it.copy(
                            isLoading = false,

                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "결제 및 주문 생성에 실패했습니다."
                        )
                    }
                }
        }
    }
}

