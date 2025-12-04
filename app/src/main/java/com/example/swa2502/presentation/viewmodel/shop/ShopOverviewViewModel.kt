package com.example.swa2502.presentation.viewmodel.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.RestaurantWithShops
import com.example.swa2502.domain.model.ShopOverviewInfo
import com.example.swa2502.domain.usecase.shop.GetRestaurantsWithShopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShopOverviewUiState(
    val isLoading: Boolean = false,
    val restaurants: List<RestaurantWithShops> = emptyList(),
    val selectedRestaurantId: Int? = null,
    val shopInfoList: List<ShopOverviewInfo> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class ShopOverviewViewModel @Inject constructor(
    private val getRestaurantsWithShopsUseCase: GetRestaurantsWithShopsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShopOverviewUiState())
    val uiState: StateFlow<ShopOverviewUiState> = _uiState.asStateFlow()

    init {
        loadRestaurants()
    }

    /**
     * 레스토랑 목록 로드
     */
    private fun loadRestaurants() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getRestaurantsWithShopsUseCase()
                .onSuccess { restaurants ->
                    val firstRestaurant = restaurants.firstOrNull()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            restaurants = restaurants,
                            selectedRestaurantId = firstRestaurant?.restaurantId,
                            shopInfoList = firstRestaurant?.let { restaurant ->
                                convertToShopOverviewInfo(restaurant)
                            } ?: emptyList(),
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            restaurants = emptyList(),
                            shopInfoList = emptyList(),
                            errorMessage = exception.message ?: "레스토랑 정보를 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    /**
     * 레스토랑 선택 시 해당 레스토랑의 가게 목록 업데이트
     */
    fun selectRestaurant(restaurantId: Int) {
        val selectedRestaurant = _uiState.value.restaurants.find { it.restaurantId == restaurantId }
        selectedRestaurant?.let { restaurant ->
            _uiState.update {
                it.copy(
                    selectedRestaurantId = restaurantId,
                    shopInfoList = convertToShopOverviewInfo(restaurant)
                )
            }
        }
    }

    /**
     * RestaurantWithShops를 ShopOverviewInfo 리스트로 변환
     */
    private fun convertToShopOverviewInfo(restaurant: RestaurantWithShops): List<ShopOverviewInfo> {
        return restaurant.shops.map { shop ->
            val queueState = when {
                shop.currentQueue < 10 -> "여유"
                shop.currentQueue < 30 -> "조금 혼잡"
                else -> "혼잡"
            }
            
            ShopOverviewInfo(
                shopId = shop.shopId.toString(),
                shopState = queueState,
                shopName = shop.name,
                queueSize = shop.currentQueue
            )
        }
    }
}
