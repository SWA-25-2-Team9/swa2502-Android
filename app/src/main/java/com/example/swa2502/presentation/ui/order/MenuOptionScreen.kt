package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.MenuOptionUiState
import com.example.swa2502.presentation.viewmodel.order.MenuOptionViewModel

@Composable
fun MenuOptionScreen(modifier: Modifier = Modifier) {
    val viewModel: MenuOptionViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MenuOptionScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun MenuOptionScreenContent(
    modifier: Modifier = Modifier,
    uiState: MenuOptionUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Menu Option Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuOptionScreenContentPreview() {
    MenuOptionScreenContent(
        modifier = Modifier,
        uiState = MenuOptionUiState(),
    )
}
