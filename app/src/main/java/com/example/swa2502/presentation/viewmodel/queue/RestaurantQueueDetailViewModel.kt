package com.example.swa2502.presentation.viewmodel.queue

import androidx.lifecycle.ViewModel
import com.example.swa2502.domain.model.ShopQueueInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class RestaurantQueueDetailUiState(
    val isLoading: Boolean = false,
    val shopQueueInfos: List<ShopQueueInfo> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class RestaurantQueueDetailViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantQueueDetailUiState())
    val uiState: StateFlow<RestaurantQueueDetailUiState> = _uiState.asStateFlow()


}

