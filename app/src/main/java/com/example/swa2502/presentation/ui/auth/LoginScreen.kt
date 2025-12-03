package com.example.swa2502.presentation.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R
import com.example.swa2502.presentation.ui.common.BottomFullWidthButton
import com.example.swa2502.presentation.ui.common.CustomTextField
import com.example.swa2502.presentation.viewmodel.auth.LoginUiState
import com.example.swa2502.presentation.viewmodel.auth.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToUserMain: () -> Unit,
    onNavigateToAdminMain: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
        onNavigateToUserMain = onNavigateToUserMain,
        onNavigateToAdminMain = onNavigateToAdminMain,
        onNavigateToSignUp = onNavigateToSignUp,
        onLoginClick = { userId, password ->
            viewModel.login(userId, password)
        }
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onNavigateToUserMain: () -> Unit,
    onNavigateToAdminMain: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onLoginClick: (String, String) -> Unit,
) {
    var id by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    
    // 로그인 성공 시 role에 따라 화면 이동
    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            when (uiState.userRole) {
                "ROLE_USER" -> onNavigateToUserMain()
                "ROLE_ADMIN" -> onNavigateToAdminMain()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ){
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = "아이디를 입력해주세요",
                color = Color.Black,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold))
                )
            )
            Spacer(modifier = Modifier.height(30.dp))

            CustomTextField(
                text = id,
                onValueChange = { id = it },
                placeholder = {
                    Text(
                        text = "ID / E-mail",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium))
                        ),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE5E5E7),
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                text = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium))
                        ),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE5E5E7),
                        shape = RoundedCornerShape(8.dp)
                    ),
                visualTransformation = PasswordVisualTransformation()
            )
            
            // 에러 메시지 표시
            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium))
                    )
                )
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BottomFullWidthButton(
                containerColor = Color(0xFFFF874A),
                contentColor = Color.White,
                text = if (uiState.isLoading) "로그인 중..." else "로그인",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                if (!uiState.isLoading && id.isNotBlank() && password.isNotBlank()) {
                    onLoginClick(id, password)
                }
            }

            BottomFullWidthButton(
                containerColor = Color.White,
                contentColor = Color(0xFFFF874A),
                text = "회원가입",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
                    .border(
                        BorderStroke(1.2.dp, Color(0xFFFF874A)),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                onNavigateToSignUp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenContentPreview() {
    LoginScreenContent(
        modifier = Modifier,
        uiState = LoginUiState(),
        onNavigateToUserMain = {},
        onNavigateToAdminMain = {},
        onNavigateToSignUp = {},
        onLoginClick = { _, _ -> }
    )
}