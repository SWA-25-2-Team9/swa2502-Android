package com.example.swa2502.presentation.ui.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.manage.ManageQueueUiState
import com.example.swa2502.presentation.viewmodel.manage.ManageQueueViewModel

@Composable
fun ManageQueueScreen(modifier: Modifier = Modifier) {
    val viewModel: ManageQueueViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ManageQueueScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun ManageQueueScreenContent(
    modifier: Modifier = Modifier,
    uiState: ManageQueueUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "주문 관리(관리자)")
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageQueueScreenContentPreview() {
    ManageQueueScreenContent(
        modifier = Modifier,
        uiState = ManageQueueUiState(),
    )
}
