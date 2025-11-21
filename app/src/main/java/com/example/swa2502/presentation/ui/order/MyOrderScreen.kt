// MyOrderScreen.kt 파일

package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.swa2502.R
import com.example.swa2502.presentation.viewmodel.order.MyOrderUiState
import com.example.swa2502.presentation.viewmodel.order.MyOrderViewModel
import com.example.swa2502.presentation.viewmodel.order.MyOrderInfo

@Composable
fun MyOrderScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
) {
    val viewModel: MyOrderViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MyOrderScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onBackClick = onBackClick,
        onCartClick = onCartClick,
    )
}

@Composable
fun MyOrderScreenContent(
    modifier: Modifier = Modifier,
    uiState: MyOrderUiState,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
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
                    text = "주문 조회",
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
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp), // 상하좌우 패딩 조정
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.orderInfo?.let {
                // 이미지 레이아웃에 맞춘 단일 주문 카드
                ImageOrderInfoCard(it)
            } ?: run {
                // 로딩/에러/주문 없음 상태 표시
                Text(
                    text = if (uiState.isLoading) "주문 정보를 불러오는 중..." else "진행 중인 주문이 없습니다.",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 32.dp)
                )
            }
        }
    }
}

// 주문 정보 카드
@Composable
fun ImageOrderInfoCard(orderInfo: MyOrderInfo) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // 가게 이름
            Text(
                text = orderInfo.storeName,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))

            // 주문 번호
            Text(
                text = "주문 번호: ${orderInfo.orderNumber}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp) // 구분선
            Spacer(modifier = Modifier.height(24.dp))

            // 메뉴 정보 (MyOrderInfo에 없으므로 더미 데이터 사용)
            MenuItemRow("메뉴 이름", "5,000원")
            Spacer(modifier = Modifier.height(4.dp))
            MenuItemRow("옵션 1", "0원")
            Spacer(modifier = Modifier.height(4.dp))
            MenuItemRow("옵션 2", "0원")
            Spacer(modifier = Modifier.height(24.dp))

            // 주문 금액
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "주문 금액",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "0원", // 임시 데이터
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp) // 구분선
            Spacer(modifier = Modifier.height(24.dp))

            // 내 순서
            Text(
                text = "내 순서: ${orderInfo.myTurn}번째",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFF5722), // 강조색
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

// 메뉴/옵션 항목 Row
@Composable
fun MenuItemRow(name: String, price: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, fontSize = 16.sp, color = Color.Black)
        Text(text = price, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun BottomNavigationBar() {
    // 현재 화면은 "주문조회"이므로 이를 Selected로 표시
    val selectedItem = "주문조회"
    val items = listOf("홈", "주문조회", "혼잡도", "마이페이지")

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(60.dp) // 이미지와 비슷한 높이 설정
    ) {
        items.forEach { item ->
            val isSelected = item == selectedItem
            NavigationBarItem(
                icon = {
                    // 이미지에 있는 아이콘 모양을 추측하여 Material Icons 또는 임시 리소스를 사용합니다.
                    // 실제 프로젝트에서는 정확한 리소스 ID (R.drawable.xxx)를 사용해야 합니다.
                    val iconResource = when (item) {
                        "홈" -> R.drawable.ic_home_gray // 가정: ic_home_outline 리소스 필요
                        "주문조회" -> R.drawable.ic_check_box_orange // 가정: ic_check_box_filled 리소스 필요
                        "혼잡도" -> R.drawable.ic_clock_gray // 가정: ic_clock_outline 리소스 필요
                        "마이페이지" -> R.drawable.ic_person_gray // 가정: ic_person_outline 리소스 필요
                        else -> R.drawable.ic_home_gray
                    }
                    val tintColor = if (isSelected) Color(0xFFFF9800) else Color(0xFF9E9E9E) // 주황색 vs 회색

                    // '주문조회'는 박스 체크 모양, '혼잡도'는 시계 모양, '홈'은 집 모양, '마이페이지'는 사람 모양으로 가정하고 리소스를 사용
                    Icon(
                        painter = painterResource(id = iconResource),
                        contentDescription = item,
                        tint = tintColor,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFFFF9800) else Color(0xFF9E9E9E)
                    )
                },
                selected = isSelected,
                onClick = { /* Navigate to screen */ },
                // 선택된 아이템의 인디케이터 색상을 흰색으로 설정하여 아이콘과 텍스트만 색상 변경되도록 함
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
    }
}

// ----------------------------------------------------
// 4. Preview
// ----------------------------------------------------

@Preview(showBackground = true, name = "주문 조회 화면 Preview")
@Composable
private fun MyOrderScreenContentPreview() {
    MyOrderScreenContent(
        modifier = Modifier,
        uiState = MyOrderUiState(
            orderInfo = MyOrderInfo(
                orderId = 1,
                orderNumber = "111번",
                storeName = "가게 이름",
                orderStatus = "주문 접수",
                estimatedWaitTime = "약 5분 후 픽업 가능",
                myTurn = 11,
                totalPeopleInQueue = 15
            )
        ),
        onBackClick = {},
        onCartClick = {}
    )
}