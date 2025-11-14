package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.OrderMenuUiState
import com.example.swa2502.presentation.viewmodel.order.OrderMenuViewModel

@Composable
fun OrderMenuScreen(modifier: Modifier = Modifier) {
    val viewModel: OrderMenuViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    OrderMenuScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun OrderMenuScreenContent(
    modifier: Modifier = Modifier,
    uiState: OrderMenuUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Order Menu Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderMenuScreenContentPreview() {
    OrderMenuScreenContent(
        modifier = Modifier,
        uiState = OrderMenuUiState(),
    )
}
