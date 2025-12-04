package com.example.swa2502.presentation.viewmodel.queue

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.ShopQueueInfo
import com.example.swa2502.domain.usecase.queue.GetRestaurantCongestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RestaurantQueueDetailUiState(
    val isLoading: Boolean = false,
    val restaurantName: String = "",
    val shopQueueInfos: List<ShopQueueInfo> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class RestaurantQueueDetailViewModel @Inject constructor(
    private val getRestaurantCongestionUseCase: GetRestaurantCongestionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantQueueDetailUiState())
    val uiState: StateFlow<RestaurantQueueDetailUiState> = _uiState.asStateFlow()

    private val restaurantId: String = savedStateHandle.get<String>("restaurantId") ?: ""

    init {
        loadQueueDetailInfo()
    }

    /**
     * 특정 레스토랑의 가게별 대기열 정보 로드
     */
    private fun loadQueueDetailInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            // restaurantId를 Int로 변환하여 API 호출
            val restaurantIdInt = restaurantId.toIntOrNull()
            if (restaurantIdInt == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        shopQueueInfos = emptyList(),
                        errorMessage = "잘못된 레스토랑 ID입니다"
                    )
                }
                return@launch
            }
            
            getRestaurantCongestionUseCase(restaurantIdInt)
                .onSuccess { restaurant ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            restaurantName = restaurant.restaurantName,
                            shopQueueInfos = restaurant.shops,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            shopQueueInfos = emptyList(),
                            errorMessage = exception.message ?: "대기열 상세 정보를 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    /**
     * 대기열 상세 정보 새로고침
     */
    fun refreshQueueDetailInfo() {
        loadQueueDetailInfo()
    }
}

