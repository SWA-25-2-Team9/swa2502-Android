package com.example.swa2502.presentation.ui.pay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R
import com.example.swa2502.presentation.viewmodel.pay.PayUiState
import com.example.swa2502.presentation.viewmodel.pay.PayViewModel
import com.example.swa2502.presentation.viewmodel.pay.PaymentMethod
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue

@Composable
fun PayScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onPayClick: () -> Unit = {}
) {
    val viewModel: PayViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 주문 생성 성공 시 네비게이션 처리
    LaunchedEffect(uiState.orderCreated) {
        if (uiState.orderCreated) {
            onPayClick()
        }
    }

    PayScreenContent(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onPaymentSelected = viewModel::selectPaymentMethod,
        onPayClick = { viewModel.createOrder() },
    )
}

@Composable
fun PayScreenContent(
    modifier: Modifier = Modifier,
    uiState: PayUiState,
    onBackClick: () -> Unit,
    onPaymentSelected: (PaymentMethod) -> Unit,
    onPayClick: () -> Unit,
) {
    val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
    val formattedPrice = formatter.format(uiState.totalPrice)

    @OptIn(ExperimentalMaterial3Api::class)

    @Composable
    fun PaymentRadioButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFFF9800), // 주황색
                    unselectedColor = Color(0xFFCCCCCC)
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 16.sp, color = Color.Black)
        }
    }

    @Composable
    fun PaymentMethodCard(
        selectedMethod: PaymentMethod,
        onPaymentSelected: (PaymentMethod) -> Unit
    ) {
        Text(
            text = "결제수단",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                PaymentRadioButton(
                    label = "신용/체크카드",
                    isSelected = selectedMethod == PaymentMethod.CARD,
                    onClick = { onPaymentSelected(PaymentMethod.CARD) }
                )
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                PaymentRadioButton(
                    label = "간편결제",
                    isSelected = selectedMethod == PaymentMethod.SIMPLE,
                    onClick = { onPaymentSelected(PaymentMethod.SIMPLE) }
                )
            }
        }
    }

    @Composable
    fun TotalPaymentRow(formattedPrice: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "총 결제금액",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "${formattedPrice}원",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722) // 강조색 주황색
            )
        }
    }
    Scaffold(
        topBar = {
            // Header 구현 (layout_header)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_orange),
                    contentDescription = "뒤로가기",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onBackClick)
                )
                Text(
                    text = "결제",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        },
        bottomBar = {
            // 하단 결제 버튼 (button_checkout)
            Button(
                onClick = onPayClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // #FF9800
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isLoading && uiState.totalPrice > 0
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                Text(
                    text = "${formattedPrice}원 결제하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                }
            }
        },
        modifier = modifier.background(Color(0xFFF5F5F5))
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            PaymentMethodCard( // 로컬 함수 호출
                selectedMethod = uiState.selectedPaymentMethod,
                onPaymentSelected = onPaymentSelected
            )
            Spacer(modifier = Modifier.height(32.dp))
            TotalPaymentRow(formattedPrice) // 로컬 함수 호출

            // 에러 메시지 표시
            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PayScreenContentPreview() {
    PayScreenContent(
        modifier = Modifier,
        uiState = PayUiState(totalPrice = 10000, selectedPaymentMethod = PaymentMethod.CARD),
        onBackClick = {},
        onPaymentSelected = {},
        onPayClick = {}
    )
}