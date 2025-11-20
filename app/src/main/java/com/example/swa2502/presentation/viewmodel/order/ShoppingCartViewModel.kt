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
data class CartOption(val name: String)
data class CartMenu(
    val menuId: Long, // 메뉴를 식별할 ID
    val menuName: String,
    val quantity: Int,
    val options: List<CartOption>,
    val totalPrice: Int, // 이 메뉴와 옵션, 수량의 총 가격 (단가 * 수량)
    val storeId: Long,
)
data class CartStore(
    val storeId: Long,
    val storeName: String,
    val cartMenus: List<CartMenu>,
)

// ----------------------------------------------------
// 2. UI 상태 정의
// ----------------------------------------------------
data class ShoppingCartUiState(
    val isLoading: Boolean = false,
    val totalAmount: Int = 0, // 전체 결제 금액
    val cartStores: List<CartStore> = emptyList(),
    val errorMessage: String? = null,
)

// ----------------------------------------------------
// 3. ViewModel 구현
// ----------------------------------------------------
@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    // private val orderRepository: OrderRepository // 실제 데이터 로직을 위한 의존성
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingCartUiState())
    val uiState: StateFlow<ShoppingCartUiState> = _uiState.asStateFlow()

    init {
        // 실제로는 Repository를 통해 카트 데이터를 불러오는 로직이 들어갑니다.
        loadShoppingCartData()
    }

    private fun loadShoppingCartData() {
        // 더미 데이터
        val dummyMenus1 = listOf(
            CartMenu(
                menuId = 101,
                menuName = "아메리카노",
                quantity = 1,
                options = listOf(CartOption("ICE (+500원)")),
                totalPrice = 5000, // 4500 + 500
                storeId = 1,
            ),
            CartMenu(
                menuId = 102,
                menuName = "카페라떼",
                quantity = 2,
                options = listOf(CartOption("HOT"), CartOption("샷 추가 (+500원)")),
                totalPrice = 11000, // (5000 + 500) * 2 = 11000
                storeId = 1,
            ),
        )

        val dummyMenus2 = listOf(
            CartMenu(
                menuId = 201,
                menuName = "크루아상",
                quantity = 3,
                options = emptyList(),
                totalPrice = 9000, // 3000 * 3
                storeId = 2,
            ),
        )

        val dummyStores = listOf(
            CartStore(storeId = 1, storeName = "커피하우스 1호점", cartMenus = dummyMenus1),
            CartStore(storeId = 2, storeName = "프리미엄 베이커리", cartMenus = dummyMenus2),
        )

        val total = dummyStores.sumOf { store -> store.cartMenus.sumOf { it.totalPrice } }

        _uiState.update {
            it.copy(
                cartStores = dummyStores,
                totalAmount = total,
                isLoading = false
            )
        }
    }

    // 카트 수량 증가/감소 (구현 필요)
    fun onQuantityChange(menuId: Long, newQuantity: Int) {
        // TODO: menuId를 찾아 수량을 변경하고 총 금액을 다시 계산하는 로직 구현
        println("메뉴 $menuId 수량 변경: $newQuantity")
    }

    // 메뉴 삭제 (구현 필요)
    fun onDeleteMenu(menuId: Long) {
        // TODO: menuId를 가진 메뉴를 카트에서 삭제하고 총 금액을 다시 계산하는 로직 구현
        println("메뉴 $menuId 삭제")
    }

    // 가게 메뉴 전체 삭제 (구현 필요)
    fun onDeleteStore(storeId: Long) {
        // TODO: storeId를 가진 가게의 모든 메뉴를 카트에서 삭제하고 총 금액을 다시 계산하는 로직 구현
        println("가게 $storeId 전체 삭제")
    }
}