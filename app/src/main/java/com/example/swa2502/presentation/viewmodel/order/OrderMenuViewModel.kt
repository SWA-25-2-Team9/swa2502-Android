package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.repository.OrderRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OrderMenuUiState(
    val isLoading: Boolean = false,
    val storeName: String = "가게 이름",
    val checkoutPrice: Int = 0,
    val menuList: List<MenuItem> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class OrderMenuViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderMenuUiState())
    val uiState: StateFlow<OrderMenuUiState> = _uiState.asStateFlow()

    private val RESTAURANT_ID = 1

    init {
        fetchMenuList()
    }

    private fun fetchMenuList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            orderRepository.getMenuList(RESTAURANT_ID)
                .onSuccess { menuList ->
                    _uiState.update {
                        it.copy(
                            menuList = menuList,
                            isLoading = false,
                            storeName = "가게이름",
                            checkoutPrice = 5000
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "메뉴 목록을 불러오는 데 실패했습니다."
                        )
                    }
                }
        }
    }

    // ✅ 수정된 부분: OrderMenuViewModel 클래스의 멤버 함수로 이동
    fun onMenuItemClick(menuItem: MenuItem) {
        // TODO: 메뉴 옵션 이동 네비게이션
        println("메뉴 클릭됨: ${menuItem.name}")
    }
}