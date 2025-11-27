package com.example.swa2502.presentation.viewmodel.pay

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.update

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
}

