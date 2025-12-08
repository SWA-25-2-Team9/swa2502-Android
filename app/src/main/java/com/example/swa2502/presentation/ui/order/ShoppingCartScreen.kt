package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
// ✅ Presentation Layer의 모델을 정확히 임포트합니다.
import com.example.swa2502.presentation.viewmodel.order.CartMenu
import com.example.swa2502.presentation.viewmodel.order.CartOption
import com.example.swa2502.presentation.viewmodel.order.CartStore
import com.example.swa2502.presentation.viewmodel.order.ShoppingCartUiState
import com.example.swa2502.presentation.viewmodel.order.ShoppingCartViewModel
import java.util.Locale

// ----------------------------------------------------
// 1. 메인 화면 Composable
// ----------------------------------------------------
@Composable
fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
) {
    val viewModel: ShoppingCartViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ShoppingCartScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onBackClick = onBackClick,
        onCheckoutClick = onCheckoutClick,
        onQuantityChange = { cartItemId, newQuantity -> viewModel.onQuantityChange(cartItemId, newQuantity) },
        onDeleteMenu = viewModel::onDeleteMenu,
        onDeleteStore = viewModel::onDeleteStore,
    )
}

// ----------------------------------------------------
// 2. 화면 구성 요소 Content
// ----------------------------------------------------
@Composable
fun ShoppingCartScreenContent(
    modifier: Modifier = Modifier,
    uiState: ShoppingCartUiState,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    onQuantityChange: (Int, Int) -> Unit,
    onDeleteMenu: (Int) -> Unit,
    onDeleteStore: (Int) -> Unit,
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

                // 메뉴 이름
                Text(
                    text = "장바구니",
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
                onClick = onCheckoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // #FF9800
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.totalAmount > 0 && !uiState.isLoading
            ) {
                Text(
                    text = "${String.format("%,d", uiState.totalAmount)}원 결제하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        modifier = modifier.background(Color(0xFFF5F5F5)) // 배경색 #F5F5F5
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text(text = uiState.errorMessage!!, color = Color.Red)
            }
        } else if (uiState.cartStores.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text(text = "카트가 비어있습니다.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.cartStores, key = { it.storeId }) { cartStore ->
                    CartStoreItem(
                        cartStore = cartStore,
                        onQuantityChange = onQuantityChange,
                        onDeleteMenu = onDeleteMenu,
                        onDeleteStore = onDeleteStore
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// 3. 상점별 카트 아이템
// ----------------------------------------------------
@Composable
fun CartStoreItem(
    cartStore: CartStore,
    onQuantityChange: (Int, Int) -> Unit,
    onDeleteMenu: (Int) -> Unit,
    onDeleteStore: (Int) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 상점 헤더
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 상점 선택/이름
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "선택 표시",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = cartStore.storeName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                }

                // 가게 메뉴 전체 삭제 버튼
                IconButton(onClick = { onDeleteStore(cartStore.storeId) }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "가게 메뉴 전체 삭제",
                        tint = Color.Gray
                    )
                }
            }

            // 메뉴 목록
            cartStore.cartMenus.forEachIndexed { index, cartMenu ->
                CartMenuItem(
                    cartMenu = cartMenu,
                    onQuantityChange = onQuantityChange,
                    onDeleteMenu = onDeleteMenu
                )

                if (index < cartStore.cartMenus.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        color = Color(0xFFEEEEEE), // #EEEEEE
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ----------------------------------------------------
// 4. 카트 메뉴 상세 아이템
// ----------------------------------------------------
@Composable
fun CartMenuItem(
    cartMenu: CartMenu,
    onQuantityChange: (Int, Int) -> Unit,
    onDeleteMenu: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // 메뉴 이름
                Text(
                    text = cartMenu.menuName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                // 옵션 목록
                cartMenu.options.forEach { option ->
                    Text(
                        text = "• ${option.name}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            // 메뉴 삭제 버튼
            IconButton(
                onClick = { onDeleteMenu(cartMenu.cartItemId) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "메뉴 삭제",
                    tint = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 수량 조절
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFEEEEEE))
            ) {
                // 감소 버튼
                IconButton(
                    onClick = { if (cartMenu.quantity > 1) onQuantityChange(cartMenu.cartItemId, cartMenu.quantity - 1) },
                    enabled = cartMenu.quantity > 1
                ) {
                    Text(text = "–", fontSize = 16.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                // 수량
                Text(
                    text = cartMenu.quantity.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                // 증가 버튼
                IconButton(
                    onClick = { onQuantityChange(cartMenu.cartItemId, cartMenu.quantity + 1) }
                ) {
                    Text(text = "+", fontSize = 16.sp, color = Color.Black)
                }
            }

            // 총 가격
            Text(
                text = "${String.format(Locale.KOREA, "%,d", cartMenu.totalPrice)}원",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


// ----------------------------------------------------
// 5. Preview Composable
// ----------------------------------------------------
@Preview(showBackground = true)
@Composable
private fun ShoppingCartScreenContentPreview() {
    val dummyState = ShoppingCartUiState(
        totalAmount = 26500,
        cartStores = listOf(
            CartStore(
                storeId = 1,
                storeName = "커피하우스 1호점",
                cartMenus = listOf(
                    CartMenu(cartItemId = 1, menuId = 101, "아메리카노", 1, listOf(CartOption("ICE (+500원)")), 5000, 1),
                    CartMenu(cartItemId = 2, menuId = 102, "카페라떼", 2, listOf(CartOption("HOT"), CartOption("샷 추가 (+500원)")), 11000, 1),
                )
            ),
            CartStore(
                storeId = 2,
                storeName = "프리미엄 베이커리",
                cartMenus = listOf(
                    CartMenu(cartItemId = 3, menuId = 201, "크루아상", 2, emptyList(), 6000, 2),
                    CartMenu(cartItemId = 4, menuId = 202, "딸기 생크림 케이크", 1, listOf(CartOption("포장 박스 추가 (+500원)")), 4500, 2),
                )
            ),
        ),
    )

    ShoppingCartScreenContent(
        uiState = dummyState,
        onBackClick = {},
        onCheckoutClick = {},
        onQuantityChange = { _, _ -> },
        onDeleteMenu = {},
        onDeleteStore = {},
    )
}

@Preview(showBackground = true, name = "Empty Cart Preview")
@Composable
private fun ShoppingCartScreenContentEmptyPreview() {
    ShoppingCartScreenContent(
        uiState = ShoppingCartUiState(cartStores = emptyList()),
        onBackClick = {},
        onCheckoutClick = {},
        onQuantityChange = { _, _ -> },
        onDeleteMenu = {},
        onDeleteStore = {},
    )
}