package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.usecase.order.GetCurrentOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ----------------------------------------------------
// 2. UI 상태 정의
// ----------------------------------------------------
data class MyOrderUiState(
    val isLoading: Boolean = false,
    val orderList: List<CurrentOrderInfo> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class MyOrderViewModel @Inject constructor(
    private val getCurrentOrderUseCase: GetCurrentOrderUseCase // 실제 Use Case 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyOrderUiState())
    val uiState: StateFlow<MyOrderUiState> = _uiState.asStateFlow()

    init {
        loadMyOrderInfo()
    }

    private fun loadMyOrderInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getCurrentOrderUseCase()
                .onSuccess { orderList ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = orderList,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = emptyList(),
                            errorMessage = exception.message ?: "주문 정보를 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    fun refreshOrderInfo() {
        loadMyOrderInfo()
    }
}
