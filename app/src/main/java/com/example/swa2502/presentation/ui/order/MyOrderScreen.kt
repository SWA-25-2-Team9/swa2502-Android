package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.MyOrderUiState
import com.example.swa2502.presentation.viewmodel.order.MyOrderViewModel

@Composable
fun MyOrderScreen(modifier: Modifier = Modifier) {
    val viewModel: MyOrderViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyOrderScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun MyOrderScreenContent(
    modifier: Modifier = Modifier,
    uiState: MyOrderUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "My Order Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyOrderScreenContentPreview() {
    MyOrderScreenContent(
        modifier = Modifier,
        uiState = MyOrderUiState(),
    )
}
