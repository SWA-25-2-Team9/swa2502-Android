package com.example.swa2502.presentation.viewmodel.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MyOrderUiState(
    val isLoading: Boolean = false,
    // 필요에 따라 상태 관리에 필요한 변수들 추가
    val errorMessage: String? = null,
)

@HiltViewModel
class MyOrderViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(MyOrderUiState())
    val uiState: StateFlow<MyOrderUiState> = _uiState.asStateFlow()


}