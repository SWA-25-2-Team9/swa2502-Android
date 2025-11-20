package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R // 프로젝트의 R 파일(드로어블)을 참조해야 합니다.
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.presentation.viewmodel.order.OrderMenuUiState
import com.example.swa2502.presentation.viewmodel.order.OrderMenuViewModel

@Composable
fun OrderMenuScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onNavigateToMenuOption: (MenuItem) -> Unit, // (A) 이 인수를 받고 있음
) {
    val viewModel: OrderMenuViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    // OrderMenuScreenContent 호출 시 누락된 모든 인수를 전달합니다.
    OrderMenuScreenContent(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.value,
        onBackClick = onBackClick, // 상위 함수에서 받은 콜백 전달
        onCartClick = onCartClick, // 상위 함수에서 받은 콜백 전달
        onMenuItemClick = { viewModel.onMenuItemClick(it) }, // ViewModel 함수와 연결
        onCheckoutClick = onCartClick // 결제 버튼은 카트로 이동하도록 연결 (또는 다른 네비게이션)
    )
}

@Composable
fun OrderMenuScreenContent(
    modifier: Modifier = Modifier,
    uiState: OrderMenuUiState,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onMenuItemClick: (MenuItem) -> Unit,
    onCheckoutClick: () -> Unit
) {
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 뒤로가기 아이콘
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_orange),
                    contentDescription = "뒤로가기",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onBackClick)
                )

                // 가게 이름 제목 (text_store_name)
                Text(
                    text = uiState.storeName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // 카트 아이콘 (icon_cart)
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "장바구니",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onCartClick)
                )
            }
        },
        bottomBar = {
            // 하단 결제 버튼 (button_checkout)
            Button(
                onClick = onCheckoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // #FF9800
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "${String.format("%,d", uiState.checkoutPrice)}원 결제 하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        modifier = modifier.background(Color(0xFFF5F5F5)) // 배경색 #F5F5F5
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // 메뉴 목록 (recycler_menu_list)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp) // 아이템 간격
            ) {
                items(uiState.menuList, key = { it.menuId }) { menuItem ->
                    MenuItemRow(menuItem = menuItem, onClick = onMenuItemClick)
                }
            }

            // 로딩 상태 표시
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.errorMessage?.let { message ->
                Text(text = message, color = Color.Red, modifier = Modifier.align(Alignment.Center).padding(16.dp))
            }
        }

    }
}

@Preview(showBackground = true, name = "메뉴 목록 기본")
@Composable
private fun OrderMenuScreenContentPreview() {
    val dummyList = listOf(
        MenuItem(1, "아메리카노", 4500, false),
        MenuItem(2, "카페라떼", 5000, false),
        MenuItem(3, "바닐라라떼 (품절)", 5500, true), // 품절 항목
        MenuItem(4, "콜드브루", 5000, false)
    )

    OrderMenuScreenContent(
        modifier = Modifier,
        uiState = OrderMenuUiState(
            menuList = dummyList,
            storeName = "프리뷰 베이커리",
            checkoutPrice = 12500,
            isLoading = false
        ),
        onBackClick = {},
        onCartClick = {},
        onMenuItemClick = {},
        onCheckoutClick = {}
    )
}

@Preview(showBackground = true, name = "메뉴 목록 - 로딩 중")
@Composable
private fun OrderMenuScreenContentLoadingPreview() {
    OrderMenuScreenContent(
        uiState = OrderMenuUiState(
            menuList = emptyList(),
            storeName = "데이터 로딩 중...",
            checkoutPrice = 0,
            isLoading = true
        ),
        onBackClick = {},
        onCartClick = {},
        onMenuItemClick = {},
        onCheckoutClick = {}
    )
}

@Preview(showBackground = true, name = "메뉴 목록 - 에러 발생")
@Composable
private fun OrderMenuScreenContentErrorPreview() {
    OrderMenuScreenContent(
        uiState = OrderMenuUiState(
            menuList = emptyList(),
            storeName = "가게 이름",
            checkoutPrice = 0,
            isLoading = false,
            errorMessage = "네트워크 연결에 문제가 발생했습니다."
        ),
        onBackClick = {},
        onCartClick = {},
        onMenuItemClick = {},
        onCheckoutClick = {}
    )
}
