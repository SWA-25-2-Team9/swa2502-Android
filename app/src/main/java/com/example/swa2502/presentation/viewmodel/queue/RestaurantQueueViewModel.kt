package com.example.swa2502.presentation.viewmodel.queue

import androidx.lifecycle.ViewModel
import com.example.swa2502.domain.model.RestaurantInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class RestaurantQueueUiState(
    val isLoading: Boolean = false,
    val restaurantList: List<RestaurantInfo> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class RestaurantQueueViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantQueueUiState())
    val uiState: StateFlow<RestaurantQueueUiState> = _uiState.asStateFlow()


}

