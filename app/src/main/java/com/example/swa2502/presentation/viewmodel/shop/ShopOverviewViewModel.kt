package com.example.swa2502.presentation.viewmodel.shop

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ShopOverviewUiState(
    val isLoading: Boolean = false,
    val restaurantList: List<String> = listOf("학생식당", "도서관 식당", "식당3"), // TODO: 이후에 수정
    val errorMessage: String? = null,
)

@HiltViewModel
class ShopOverviewViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(ShopOverviewUiState())
    val uiState: StateFlow<ShopOverviewUiState> = _uiState.asStateFlow()


}

