package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.ShoppingCartUiState
import com.example.swa2502.presentation.viewmodel.order.ShoppingCartViewModel

@Composable
fun ShoppingCartScreen(modifier: Modifier = Modifier) {
    val viewModel: ShoppingCartViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ShoppingCartScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun ShoppingCartScreenContent(
    modifier: Modifier = Modifier,
    uiState: ShoppingCartUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Shopping Cart Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingCartScreenContentPreview() {
    ShoppingCartScreenContent(
        modifier = Modifier,
        uiState = ShoppingCartUiState(),
    )
}
