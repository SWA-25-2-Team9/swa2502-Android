package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// ----------------------------------------------------
// 1. 데이터 모델 정의
// ----------------------------------------------------
data class OptionItem(
    val id: Int,
    val name: String,
    val price: Int, // 추가 가격 (0원 이상)
)

data class OptionGroup(
    val id: Int,
    val name: String,
    val isRequired: Boolean, // 필수 선택 여부
    val options: List<OptionItem>,
    val selectedOptionId: Int? = null // 선택된 옵션 ID
)

// ----------------------------------------------------
// 2. UI 상태 정의
// ----------------------------------------------------
data class MenuOptionUiState(
    val isLoading: Boolean = false,
    val menuName: String = "메뉴 이름",
    val basePrice: Int = 4500,
    val optionGroups: List<OptionGroup> = emptyList(),
    val quantity: Int = 1, // 수량은 현재 1로 고정
    val totalAmount: Int = 4500,
    val errorMessage: String? = null,
)

// ----------------------------------------------------
// 3. ViewModel 구현
// ----------------------------------------------------
@HiltViewModel
class MenuOptionViewModel @Inject constructor(
    // private val orderRepository: OrderRepository // 추후 Repository 사용 시 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuOptionUiState())
    val uiState: StateFlow<MenuOptionUiState> = _uiState.asStateFlow()

    init {
        // 실제로는 Navigation Argument로 받은 메뉴 ID를 이용해 메뉴 상세 정보를 로드합니다.
        // 여기서는 더미 데이터를 사용합니다.
        loadMenuDetail(menuItemId = 1)
    }

    private fun loadMenuDetail(menuItemId: Int) {
        // 이미지 화면에 맞춘 더미 데이터
        val dummyOptionGroups = listOf(
            OptionGroup(
                id = 1,
                name = "온도",
                isRequired = true,
                options = listOf(
                    OptionItem(id = 101, name = "HOT", price = 0),
                    OptionItem(id = 102, name = "ICE (+500원)", price = 500)
                ),
                selectedOptionId = 101 // 기본 선택
            ),
            OptionGroup(
                id = 2,
                name = "사이즈",
                isRequired = false,
                options = listOf(
                    OptionItem(id = 201, name = "Large (+500원)", price = 500),
                    OptionItem(id = 202, name = "Extra Large (+1000원)", price = 1000)
                ),
                selectedOptionId = 201 // 기본 선택
            )
        )
        val basePrice = 4500 // 아메리카노 기준
        _uiState.update {
            it.copy(
                menuName = "아메리카노",
                basePrice = basePrice,
                optionGroups = dummyOptionGroups,
                totalAmount = calculateTotal(basePrice, 1, dummyOptionGroups)
            )
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