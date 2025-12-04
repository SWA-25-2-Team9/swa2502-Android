package com.example.swa2502.presentation.viewmodel.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swa2502.domain.model.RestaurantInfo
import com.example.swa2502.domain.usecase.queue.GetQueueInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RestaurantQueueUiState(
    val isLoading: Boolean = false,
    val restaurantList: List<RestaurantInfo> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class RestaurantQueueViewModel @Inject constructor(
    private val getQueueInfoUseCase: GetQueueInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantQueueUiState())
    val uiState: StateFlow<RestaurantQueueUiState> = _uiState.asStateFlow()

    init {
        loadQueueInfo()
    }

    /**
     * 대기열 및 혼잡도 정보 로드
     */
    private fun loadQueueInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            getQueueInfoUseCase()
                .onSuccess { restaurantList ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            restaurantList = restaurantList,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            restaurantList = emptyList(),
                            errorMessage = exception.message ?: "대기열 정보를 불러오는데 실패했습니다"
                        )
                    }
                }
        }
    }

    /**
     * 대기열 정보 새로고침
     */
    fun refreshQueueInfo() {
        loadQueueInfo()
    }
}

