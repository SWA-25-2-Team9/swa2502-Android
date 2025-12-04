package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.domain.usecase.order.GetMenuListUseCase // UseCase 임포트
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
    private val getMenuListUseCase: GetMenuListUseCase // UseCase 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderMenuUiState())
    val uiState: StateFlow<OrderMenuUiState> = _uiState.asStateFlow()

    // TODO: Navigation Argument 등을 통해 실제 shopId를 받아와야 합니다.
    private val SHOP_ID = 1

    init {
        fetchMenuList()
    }

    private fun fetchMenuList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // UseCase 호출 및 SHOP_ID 전달
            getMenuListUseCase(SHOP_ID)
                .onSuccess { menuList ->
                    _uiState.update {
                        it.copy(
                            menuList = menuList,
                            isLoading = false,
                            // 실제 가게 이름 업데이트 (현재는 더미)
                            storeName = "API 연동 가게 이름",
                            checkoutPrice = 0 // 장바구니에 담긴 메뉴에 따라 계산
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

    /**
     * 메뉴 아이템 클릭 이벤트 처리
     * @param menuItem 클릭된 메뉴 아이템
     */
    fun onMenuItemClick(menuItem: MenuItem) {
        // TODO: 장바구니 상태 업데이트, 분석 로깅 등 추가 비즈니스 로직 처리
        println("메뉴 클릭됨: ${menuItem.name}")
    }
}