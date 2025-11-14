package com.example.swa2502.presentation.ui.queue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueUiState
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueViewModel

@Composable
fun RestaurantQueueScreen(modifier: Modifier = Modifier) {
    val viewModel: RestaurantQueueViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RestaurantQueueScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun RestaurantQueueScreenContent(
    modifier: Modifier = Modifier,
    uiState: RestaurantQueueUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "혼잡도")
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantQueueScreenContentPreview() {
    RestaurantQueueScreenContent(
        modifier = Modifier,
        uiState = RestaurantQueueUiState(),
    )
}
