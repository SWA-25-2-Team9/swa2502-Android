package com.example.swa2502.presentation.ui.shop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.shop.ShopOverviewUiState
import com.example.swa2502.presentation.viewmodel.shop.ShopOverviewViewModel

@Composable
fun ShopOverviewScreen(modifier: Modifier = Modifier) {
    val viewModel: ShopOverviewViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ShopOverviewScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun ShopOverviewScreenContent(
    modifier: Modifier = Modifier,
    uiState: ShopOverviewUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "사용자 메인")
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopOverviewScreenContentPreview() {
    ShopOverviewScreenContent(
        modifier = Modifier,
        uiState = ShopOverviewUiState(),
    )
}
