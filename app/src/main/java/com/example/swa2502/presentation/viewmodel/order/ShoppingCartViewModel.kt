package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.swa2502.domain.usecase.order.GetShoppingCartInfoUseCase
import com.example.swa2502.domain.usecase.order.UpdateCartItemQuantityUseCase
import com.example.swa2502.domain.usecase.order.DeleteCartItemUseCase
import com.example.swa2502.domain.usecase.order.ClearShoppingCartUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.swa2502.domain.model.CartStore as DomainCartStore // ✅ Domain 모델은 매핑에 사용하기 위해 별칭 임포트


data class CartOption(val name: String)
data class CartMenu(
    val cartItemId: Int,
    val menuId: Int,
    val menuName: String,
    val quantity: Int,
    val options: List<CartOption>,
    val totalPrice: Int,
    val storeId: Int,
)
data class CartStore(
    val storeId: Int,
    val storeName: String,
    val cartMenus: List<CartMenu>,
)

// ui 상태 정의
data class ShoppingCartUiState(
    val isLoading: Boolean = false,
    val totalAmount: Int = 0,
    val cartStores: List<CartStore> = emptyList(),
    val errorMessage: String? = null,
)

// view model 구현
@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getShoppingCartInfoUseCase: GetShoppingCartInfoUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val clearShoppingCartUseCase: ClearShoppingCartUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingCartUiState())
    val uiState: StateFlow<ShoppingCartUiState> = _uiState.asStateFlow()

    init {
        loadShoppingCartInfo()
    }

    private fun loadShoppingCartInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            getShoppingCartInfoUseCase()
                .onSuccess { domainCartStores ->
                    // Domain Model -> Presentation Model 매핑
                    val presentationCartStores = domainCartStores.map { domainStore ->
                        CartStore(
                            storeId = domainStore.storeId,
                            storeName = domainStore.storeName,
                            cartMenus = domainStore.cartMenus.map { domainMenu ->
                                CartMenu(
                                    cartItemId = domainMenu.cartItemId,
                                    menuId = domainMenu.menuId,
                                    menuName = domainMenu.menuName,
                                    quantity = domainMenu.quantity,
                                    options = domainMenu.options.map { domainOption ->
                                        CartOption(name = domainOption.name)
                                    },
                                    totalPrice = domainMenu.totalPrice,
                                    storeId = domainMenu.storeId
                                )
                            }
                        )
                    }

                    val total = presentationCartStores.sumOf { store -> store.cartMenus.sumOf { it.totalPrice } }
                    _uiState.update {
                        it.copy(
                            cartStores = presentationCartStores,
                            totalAmount = total,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            cartStores = emptyList(),
                            totalAmount = 0,
                            errorMessage = error.message ?: "장바구니 정보를 불러오는 데 실패했습니다."
                        )
                    }
                }
        }
    }

    // 카트 수량 증가/감소
    fun onQuantityChange(cartItemId: Int, newQuantity: Int) {
        if (newQuantity <= 0) return

        viewModelScope.launch {
            updateCartItemQuantityUseCase(cartItemId, newQuantity)
                .onSuccess { loadShoppingCartInfo() }
                .onFailure { error ->
                    _uiState.update { it.copy(errorMessage = error.message ?: "수량 변경에 실패했습니다.") }
                    loadShoppingCartInfo()
                }
        }
    }

    // 메뉴 삭제
    fun onDeleteMenu(cartItemId: Int) {
        viewModelScope.launch {
            deleteCartItemUseCase(cartItemId)
                .onSuccess { loadShoppingCartInfo() }
                .onFailure { error ->
                    _uiState.update { it.copy(errorMessage = error.message ?: "메뉴 삭제에 실패했습니다.") }
                    loadShoppingCartInfo()
                }
        }
    }

    // 가게 메뉴 전체 삭제
    fun onDeleteStore(storeId: Int) {
        viewModelScope.launch {
            clearShoppingCartUseCase()
                .onSuccess {
                    _uiState.update { it.copy(cartStores = emptyList(), totalAmount = 0, errorMessage = null, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(errorMessage = error.message ?: "장바구니 전체 비우기에 실패했습니다.") }
                    loadShoppingCartInfo()
                }
        }
    }

    fun refreshShoppingCart() {
        loadShoppingCartInfo()
    }
}