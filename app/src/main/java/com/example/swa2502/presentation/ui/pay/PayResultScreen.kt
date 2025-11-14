package com.example.swa2502.presentation.ui.pay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.pay.PayResultUiState
import com.example.swa2502.presentation.viewmodel.pay.PayResultViewModel

@Composable
fun PayResultScreen(modifier: Modifier = Modifier) {
    val viewModel: PayResultViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PayResultScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun PayResultScreenContent(
    modifier: Modifier = Modifier,
    uiState: PayResultUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "결제 결과")
    }
}

@Preview(showBackground = true)
@Composable
private fun PayResultScreenContentPreview() {
    PayResultScreenContent(
        modifier = Modifier,
        uiState = PayResultUiState(),
    )
}
