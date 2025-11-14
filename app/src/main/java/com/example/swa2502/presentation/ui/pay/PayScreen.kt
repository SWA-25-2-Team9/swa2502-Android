package com.example.swa2502.presentation.ui.pay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.pay.PayUiState
import com.example.swa2502.presentation.viewmodel.pay.PayViewModel

@Composable
fun PayScreen(modifier: Modifier = Modifier) {
    val viewModel: PayViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PayScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun PayScreenContent(
    modifier: Modifier = Modifier,
    uiState: PayUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "결제")
    }
}

@Preview(showBackground = true)
@Composable
private fun PayScreenContentPreview() {
    PayScreenContent(
        modifier = Modifier,
        uiState = PayUiState(),
    )
}
