package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.DisposableEffect
import com.example.swa2502.R
import com.example.swa2502.domain.model.MenuItem
import com.example.swa2502.presentation.viewmodel.order.OrderMenuUiState
import com.example.swa2502.presentation.viewmodel.order.OrderMenuViewModel
import com.example.swa2502.presentation.ui.order.component.MenuItemRow

@Composable
fun OrderMenuScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onNavigateToMenuOption: (MenuItem) -> Unit, // 메뉴 옵션 화면으로 이동 콜백
) {
    val viewModel: OrderMenuViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    // 화면이 resume될 때마다 장바구니 총 금액 갱신
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshCartTotal()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    OrderMenuScreenContent(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onCartClick = onCartClick,
        onMenuItemClick = { menuItem ->
            viewModel.onMenuItemClick(menuItem)
            onNavigateToMenuOption(menuItem)
        },
        onCheckoutClick = onCartClick // 장바구니 화면으로 이동
    )

    // TODO: 에러 메시지 표시 Snackbar 로직 추가
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderMenuScreenContent(
    modifier: Modifier = Modifier,
    uiState: OrderMenuUiState,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onMenuItemClick: (MenuItem) -> Unit,
    onCheckoutClick: () -> Unit,
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

                // 가게 이름
                Text(
                    text = "${uiState.storeName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // 카트 아이콘
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart_orange),
                    contentDescription = "카트",
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
                    text = "${String.format("%,d", uiState.checkoutPrice)}원 결제하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        modifier = modifier.background(Color(0xFFF5F5F5)) // 배경색 #F5F5F5
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("오류: ${uiState.errorMessage}", color = Color.Red)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(uiState.menuList) { menuItem ->
                    MenuItemRow(
                        menuItem = menuItem,
                        onClick = onMenuItemClick
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

// Preview는 수정된 UiState와 MenuItem 구조를 반영합니다.
@Preview(showBackground = true, name = "메뉴 목록")
@Composable
private fun OrderMenuScreenContentPreview() {
    val dummyList = listOf(
        MenuItem(1, "아메리카노", 4500, null, false),
        MenuItem(2, "카페라떼", 5000, null, false),
        MenuItem(3, "바닐라라떼 (품절)", 5500, null, true), // 품절 항목
        MenuItem(4, "콜드브루", 5000, null, false)
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