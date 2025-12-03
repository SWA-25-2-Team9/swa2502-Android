package com.example.swa2502.presentation.viewmodel.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.Order
import com.example.swa2502.domain.usecase.manage.GetOrdersUseCase
import com.example.swa2502.domain.usecase.manage.UpdateOrderStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManageQueueUiState(
    val isLoading: Boolean = false,
    // 필요에 따라 상태 관리에 필요한 변수들 추가
    val orderList: List<Order> = emptyList(),
    val errorMessage: String? = null,
    val selectedTab: String = "전체 주문",
)

@HiltViewModel
class ManageQueueViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ManageQueueUiState())
    val uiState: StateFlow<ManageQueueUiState> = _uiState.asStateFlow()

    // TODO: 실제 shopId를 어디서 가져올지 결정 필요 (로그인 정보에서 가져오거나, 파라미터로 전달받기)
    private val shopId = 1 // 임시로 1 사용

    init {
        getAllOrders()
    }

    // 전체 주문 불러오기
    fun getAllOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getOrdersUseCase(shopId)
                .onSuccess { orders ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = orders,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = emptyList(),
                            errorMessage = exception.message ?: "주문 목록을 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    // 조리중인 주문 불러오기
    fun getOrdersInProgress() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getOrdersUseCase.getOrdersInProgress(shopId)
                .onSuccess { orders ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = orders,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = emptyList(),
                            errorMessage = exception.message ?: "조리중 주문 목록을 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    // 수령 대기중인 주문 불러오기
    fun getPreparedOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getOrdersUseCase.getPreparedOrders(shopId)
                .onSuccess { orders ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = orders,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderList = emptyList(),
                            errorMessage = exception.message ?: "수령 대기 주문 목록을 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    // 탭 선택 변경
    fun selectTab(tab: String) {
        _uiState.update { it.copy(selectedTab = tab) }
        when (tab) {
            "전체 주문" -> getAllOrders()
            "조리중" -> getOrdersInProgress()
            "수령 대기" -> getPreparedOrders()
        }
    }
    
    /**
     * 주문 상태를 다음 단계로 변경하고 목록 새로고침
     * @param orderItemId 주문 항목 ID
     */
    fun updateOrderStatus(orderItemId: String) {
        viewModelScope.launch {
            updateOrderStatusUseCase(orderItemId.toInt())
                .onSuccess {
                    // 상태 변경 성공 시 현재 선택된 탭에 따라 목록 새로고침
                    refreshCurrentTab()
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            errorMessage = exception.message ?: "주문 상태 변경에 실패했습니다"
                        )
                    }
                }
        }
    }
    
    /**
     * 현재 선택된 탭의 목록을 새로고침
     */
    private fun refreshCurrentTab() {
        when (_uiState.value.selectedTab) {
            "전체 주문" -> getAllOrders()
            "조리중" -> getOrdersInProgress()
            "수령 대기" -> getPreparedOrders()
        }
    }
}

