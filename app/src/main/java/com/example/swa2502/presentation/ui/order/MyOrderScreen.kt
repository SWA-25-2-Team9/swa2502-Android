package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R
import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.model.OrderItem
import com.example.swa2502.presentation.ui.order.component.CurrentOrderCard
import com.example.swa2502.presentation.viewmodel.order.MyOrderUiState
import com.example.swa2502.presentation.viewmodel.order.MyOrderViewModel


// 공통 스타일 및 색상 정의
private val BackgroundGray = Color(0xFFF5F5F5)
private val PrimaryColor = Color(0xFFFF9800)


@Composable
fun MyOrderScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
) {
    val viewModel: MyOrderViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyOrderScreenContent(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onCartClick = onCartClick,
        onRefresh = viewModel::refreshOrderInfo
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrderScreenContent(
    modifier: Modifier = Modifier,
    uiState: MyOrderUiState,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "식당 혼잡도",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGray)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center), color = PrimaryColor)
                }
                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "⚠️ 오류 발생", color = Color.Red)
                        Text(text = uiState.errorMessage ?: "정보를 불러오는데 실패했습니다.", textAlign = TextAlign.Center)
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = onRefresh) { Text("새로고침") }
                    }
                }
                uiState.orderList.isEmpty() -> {
                    Text(
                        text = "현재 진행 중인 주문이 없습니다.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    // 주문 목록 표시
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.orderList) { order ->
                            CurrentOrderCard(orderInfo = order) // 분리된 컴포넌트 사용
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyOrderScreenContentPreview() {
    val dummyOrderList = listOf(
        CurrentOrderInfo(
            orderId = 2,
            orderNumber = "1번",
            shopName = "바비든든",
            myTurn = 2,
            etaMinutes = 15,
            estimatedWaitTime = "약 15분 예상",
            totalPrice = 15000,
            orderedAt = "2025-12-04T12:47:21",
            items = listOf(
                OrderItem(menuName = "고기덮밥", quantity = 3, price = 5000, options = listOf("보통"))
            )
        ),
        CurrentOrderInfo(
            orderId = 3,
            orderNumber = "123번",
            shopName = "가게 이름1",
            myTurn = 11,
            etaMinutes = 20,
            estimatedWaitTime = "약 20분 예상",
            totalPrice = 5000,
            orderedAt = "2025-12-04T12:30:00",
            items = listOf(
                OrderItem(menuName = "메뉴 감영", quantity = 1, price = 5000, options = listOf("매운맛 추가"))
            )
        )
    )

    MyOrderScreenContent(
        uiState = MyOrderUiState(
            orderList = dummyOrderList,
            isLoading = false,
            errorMessage = null
        ),
        onBackClick = {},
        onCartClick = {},
        onRefresh = {}
    )
}