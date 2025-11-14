package com.example.swa2502.presentation.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.mypage.MyPageUiState
import com.example.swa2502.presentation.viewmodel.mypage.MyPageViewModel

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    val viewModel: MyPageViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun MyPageScreenContent(
    modifier: Modifier = Modifier,
    uiState: MyPageUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "마이페이지")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MyPageScreenContent(
        modifier = Modifier,
        uiState = MyPageUiState(),
    )
}
