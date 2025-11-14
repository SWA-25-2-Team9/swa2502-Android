package com.example.swa2502.presentation.ui.queue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueDetailUiState
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueDetailViewModel

@Composable
fun RestaurantQueueDetailScreen(modifier: Modifier = Modifier) {
    val viewModel: RestaurantQueueDetailViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RestaurantQueueDetailScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun RestaurantQueueDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: RestaurantQueueDetailUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Restaurant Queue Detail Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantQueueDetailScreenContentPreview() {
    RestaurantQueueDetailScreenContent(
        modifier = Modifier,
        uiState = RestaurantQueueDetailUiState(),
    )
}
