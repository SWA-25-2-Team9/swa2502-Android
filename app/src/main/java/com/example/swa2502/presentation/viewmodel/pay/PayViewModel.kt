package com.example.swa2502.presentation.viewmodel.pay

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.update
import com.example.swa2502.domain.usecase.order.CreateOrderUseCase
import com.example.swa2502.domain.usecase.order.GetShoppingCartInfoUseCase
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
    val orderCreated: Boolean = false, // 주문 생성 성공 여부
)

@HiltViewModel
class PayViewModel @Inject constructor(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getShoppingCartInfoUseCase: GetShoppingCartInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PayUiState())
    val uiState: StateFlow<PayUiState> = _uiState.asStateFlow()

    init {
        // 장바구니 정보를 가져와서 총 금액 계산
        loadPaymentInfo()
    }

    private fun loadPaymentInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            getShoppingCartInfoUseCase()
                .onSuccess { cartStores ->
                    // 장바구니의 모든 항목의 총 금액 계산
                    val total = cartStores.sumOf { store ->
                        store.cartMenus.sumOf { it.totalPrice }
                    }
                    _uiState.update {
                        it.copy(
                            totalPrice = total,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            totalPrice = 0,
                            isLoading = false,
                            errorMessage = error.message ?: "장바구니 정보를 불러오는 데 실패했습니다."
                        )
                    }
                }
        }
    }

    fun selectPaymentMethod(method: PaymentMethod) {
        _uiState.update {
            it.copy(selectedPaymentMethod = method)
        }
    }

    fun createOrder(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, orderCreated = false) }

            // 실제 결제는 이루어지지 않으므로 paymentMethod는 고려하지 않고 빈 문자열 전달
            // 주문 생성만 수행
            createOrderUseCase("")
                .onSuccess {
                    // 주문 생성 완료
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderCreated = true,
                            errorMessage = null
                        )
                    }
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderCreated = false,
                            errorMessage = error.message ?: "주문 생성에 실패했습니다."
                        )
                    }
                }
        }
    }
}

