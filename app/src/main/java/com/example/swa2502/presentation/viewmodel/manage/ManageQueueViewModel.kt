package com.example.swa2502.presentation.viewmodel.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.Order
import com.example.swa2502.domain.usecase.manage.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageQueueUiState(
    val isLoading: Boolean = false,
    // 필요에 따라 상태 관리에 필요한 변수들 추가
    val orderList: List<Order> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class ManageQueueViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ManageQueueUiState())
    val uiState: StateFlow<ManageQueueUiState> = _uiState.asStateFlow()

    init {
        getAllOrders()
    }

    // 전체 주문 불러오기
    fun getAllOrders() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true)
            try{
                val orders = getOrdersUseCase.getAllOrders()
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    orderList = orders
                )
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    // 조리중인 주문 불러오기
    fun getOrdersInProgress() {

    }

    // 수령 대기중인 주문 불러오기
    fun getPreparedOrders() {

    }
}

