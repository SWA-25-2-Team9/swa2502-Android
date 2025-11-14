package com.example.swa2502.presentation.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.auth.LoginUiState
import com.example.swa2502.presentation.viewmodel.auth.LoginViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Login Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenContentPreview() {
    LoginScreenContent(
        modifier = Modifier,
        uiState = LoginUiState(),
    )
}