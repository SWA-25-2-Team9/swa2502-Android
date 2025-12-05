package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.OptionItem
import com.example.swa2502.domain.model.OptionGroup
import com.example.swa2502.domain.usecase.order.GetMenuDetailUseCase
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle



// ui 상태 정의
data class MenuOptionUiState(
    val isLoading: Boolean = false,
    val menuName: String = "메뉴 이름",
    val basePrice: Int = 0,
    val optionGroups: List<OptionGroup> = emptyList(),
    val quantity: Int = 1, // 수량은 현재 1로 고정
    val totalAmount: Int = 0,
    val errorMessage: String? = null,
)


@HiltViewModel
class MenuOptionViewModel @Inject constructor(
    private val getMenuDetailUseCase: GetMenuDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuOptionUiState())
    val uiState: StateFlow<MenuOptionUiState> = _uiState.asStateFlow()

    private val menuId: Int = savedStateHandle.get<Int>("menuId") ?: -1

    init {
        if (menuId != -1) {
            loadMenuOption()
        } else {
            _uiState.update { it.copy(errorMessage = "메뉴 ID를 찾을 수 없습니다.") }
        }
    }

    private fun loadMenuOption() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getMenuDetailUseCase(menuId)
                .onSuccess { menuDetail ->
                    // 초기 총 금액 계산 (기본 가격 + 기본 선택 옵션 가격)
                    val initialTotal = calculateTotal(menuDetail.basePrice, _uiState.value.quantity, menuDetail.optionGroups)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            menuName = menuDetail.name,
                            basePrice = menuDetail.basePrice,
                            optionGroups = menuDetail.optionGroups,
                            totalAmount = initialTotal,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "메뉴 옵션 정보를 불러오는 데 실패했습니다."
                        )
                    }
                }
        }
    }

    // 옵션 선택 처리
    fun onOptionSelected(groupId: Int, optionId: Int) {
        val newGroups = _uiState.value.optionGroups.map { group ->
            if (group.id == groupId) {
                group.copy(selectedOptionId = optionId)
            } else {
                group
            }
        }
        val newTotal = calculateTotal(_uiState.value.basePrice, _uiState.value.quantity, newGroups)

        _uiState.update {
            it.copy(optionGroups = newGroups, totalAmount = newTotal)
        }
    }

    // 수량 증가
    fun onQuantityIncrease() {
        val newQuantity = _uiState.value.quantity + 1
        val newTotal = calculateTotal(_uiState.value.basePrice, newQuantity, _uiState.value.optionGroups)
        _uiState.update { it.copy(quantity = newQuantity, totalAmount = newTotal) }
    }

    // 수량 감소
    fun onQuantityDecrease() {
        if (_uiState.value.quantity > 1) {
            val newQuantity = _uiState.value.quantity - 1
            val newTotal = calculateTotal(_uiState.value.basePrice, newQuantity, _uiState.value.optionGroups)
            _uiState.update { it.copy(quantity = newQuantity, totalAmount = newTotal) }
        }
    }

    // 최종 가격 계산 로직
    private fun calculateTotal(basePrice: Int, quantity: Int, groups: List<OptionGroup>): Int {
        val selectedOptionPrice = groups.sumOf { group ->
            group.options.find { it.id == group.selectedOptionId }?.price ?: 0
        }
        return (basePrice + selectedOptionPrice) * quantity
    }

    // 카트 담기 로직
    fun onAddToCartClick() {
        // TODO: 현재 선택된 옵션과 수량을 포함하여 카트 Repository에 추가하는 로직 구현
        println("카트에 담기: ${_uiState.value.menuName}, 총 금액: ${_uiState.value.totalAmount}원")
    }
}