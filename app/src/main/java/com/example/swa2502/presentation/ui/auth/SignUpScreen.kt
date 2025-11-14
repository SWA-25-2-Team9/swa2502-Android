package com.example.swa2502.presentation.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.auth.SignUpUiState
import com.example.swa2502.presentation.viewmodel.auth.SignUpViewModel

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "회원가입")
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenContentPreview() {
    SignUpScreenContent(
        modifier = Modifier,
        uiState = SignUpUiState(),
    )
}
