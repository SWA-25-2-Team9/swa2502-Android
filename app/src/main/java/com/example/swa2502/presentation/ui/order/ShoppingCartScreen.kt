package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.ShoppingCartUiState
import com.example.swa2502.presentation.viewmodel.order.ShoppingCartViewModel
// 필요한 다른 import들은 여기에 추가되어야 합니다.


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
    // 실제 ViewModel을 사용하려면 uiState를 collectAsStateWithLifecycle로 받아야 합니다.
    // val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    // 현재는 더미 데이터를 사용하는 Preview를 위해 주석 처리합니다.
    // ShoppingCartScreenContent(
    //     modifier = modifier,
    //     uiState = uiState.value,
    //     onBackClick = onBackClick,
    //     onCheckoutClick = onCheckoutClick,
    //     // ... 필요한 이벤트 핸들러 추가
    // )
}


// ----------------------------------------------------
// 2. Content Composable (모든 UI 요소 포함)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreenContent(
    modifier: Modifier = Modifier,
    uiState: ShoppingCartUiState,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    // TODO: 카트 아이템 삭제/수량 변경 등 이벤트 핸들러 추가
) {
    // TopBar: OrderMenuScreen 스타일 통일
    @Composable
    fun ShoppingCartTopBar() {
        TopAppBar(
            title = {
                Text(
                    text = "카트",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint = Color(0xFFFF9800)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
    }

    // BottomBar: 결제 금액 및 버튼 (activity_cart.xml 및 OrderMenuScreen 스타일 통일)
    @Composable
    fun ShoppingCartBottomBar() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 16.dp), // 금액 요약 위쪽 패딩
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. 결제 금액 요약
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                val (label, amount) = createRefs()

                Text(
                    text = "결제 금액",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(label) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )

                Text(
                    text = "${String.format("%,d", uiState.totalAmount)}원",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF5722), // #FF5722 (금액 강조색)
                    modifier = Modifier.constrainAs(amount) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }

            // 2. 결제 하기 버튼
            Button(
                onClick = onCheckoutClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // #FF9800
                shape = RoundedCornerShape(0.dp), // 🚨 0dp 모서리로 통일
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp) // 🚨 56dp 높이로 통일
            ) {
                Text(
                    text = "${String.format("%,d", uiState.totalAmount)}원 결제 하기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

    Scaffold(
        topBar = { ShoppingCartTopBar() },
        bottomBar = { ShoppingCartBottomBar() },
        modifier = modifier.background(Color(0xFFF5F5F5))
    ) { paddingValues ->
        // 카트 내용 목록 (LazyColumn)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp) // 🚨 Box 너비/Content Padding 통일 (8.dp)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(vertical = 8.dp), // 🚨 Content Padding 통일 (8.dp)
        ) {
            items(uiState.cartStores) { cartStore ->
                CartStoreItem(cartStore = cartStore)
            }

            // 리스트 하단에 공간 추가 (BottomBar가 가리는 부분 고려)
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ----------------------------------------------------
// 3. 보조 Composable: 매장별 카트 항목 (item_cart_store.xml 참조)
// ----------------------------------------------------
@Composable
fun CartStoreItem(cartStore: CartStore, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // xml 참조: CardElevation="0dp"
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // LazyColumn의 8dp 패딩에 맞춰 Card 외부 간격 조절
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // 1. 매장 이름 및 삭제 아이콘 (ConstraintLayout)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                val (dot, name, deleteIcon) = createRefs()

                Icon(
                    imageVector = Icons.Filled.Close, // 임시: 둥근 점이 없으므로 Close 아이콘 사용
                    contentDescription = "선택 표시",
                    tint = Color(0xFFFF9800), // #FF9800
                    modifier = Modifier
                        .size(16.dp)
                        .constrainAs(dot) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(
                    text = cartStore.storeName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(dot.end, margin = 8.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(deleteIcon.start, margin = 8.dp)
                            width = Dimension.fillToConstraints
                        }
                )

                IconButton(
                    onClick = { /* TODO: 매장 메뉴 전체 삭제 */ },
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(deleteIcon) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close, // 회색 X 아이콘
                        contentDescription = "가게 메뉴 전체 삭제",
                        tint = Color(0xFF777777) // #777777
                    )
                }
            }

            // 2. 메뉴 상세 목록 (Layout은 item_cart_menu_detail.xml이 필요하나, 현재는 함수로 대체)
            cartStore.cartMenus.forEachIndexed { index, cartMenu ->
                CartMenuDetailItem(cartMenu = cartMenu)

                if (index < cartStore.cartMenus.lastIndex) {
                    // 메뉴 항목 사이에 구분선 추가 (item_cart_store.xml 참조)
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
// 4. 보조 Composable: 단일 메뉴 상세 항목
// ----------------------------------------------------
@Composable
fun CartMenuDetailItem(cartMenu: CartMenu, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        // 메뉴 이름 (수량 포함)
        Text(
            text = "${cartMenu.menuName} x ${cartMenu.quantity}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))

        // 옵션 목록
        if (cartMenu.options.isNotEmpty()) {
            Text(
                text = cartMenu.options.joinToString(separator = ", ") { it.name },
                fontSize = 14.sp,
                color = Color(0xFF777777) // #777777
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        // 메뉴 총 가격
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${String.format("%,d", cartMenu.totalPrice)}원",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800) // #FF9800
            )

            // TODO: 수량 조절 버튼 (이전 xml에는 없지만, 일반적인 카트 화면에는 필요)
            // 임시로 수정/삭제 버튼 공간을 둡니다.
            Row {
                Text(
                    text = "수정",
                    color = Color(0xFF777777),
                    modifier = Modifier.clickable { /* TODO: 수정 */ }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "삭제",
                    color = Color(0xFF777777),
                    modifier = Modifier.clickable { /* TODO: 삭제 */ }
                )
            }
        }
    }
}

// ----------------------------------------------------
// 5. 임시 데이터 모델 (ViewModel 파일에 정의되어야 함)
// ----------------------------------------------------
data class CartOption(val name: String)
data class CartMenu(
    val menuName: String,
    val quantity: Int,
    val options: List<CartOption>,
    val totalPrice: Int,
)
data class CartStore(
    val storeName: String,
    val cartMenus: List<CartMenu>,
)
data class ShoppingCartUiState(
    val isLoading: Boolean = false,
    val totalAmount: Int = 0,
    val cartStores: List<CartStore> = emptyList(),
    val errorMessage: String? = null,
)

// ----------------------------------------------------
// 6. Preview Composable
// ----------------------------------------------------
@Preview(showBackground = true)
@Composable
private fun ShoppingCartScreenContentPreview() {
    val dummyState = ShoppingCartUiState(
        totalAmount = 15500,
        cartStores = listOf(
            CartStore(
                storeName = "커피하우스1호점",
                cartMenus = listOf(
                    CartMenu("아메리카노", 1, listOf(CartOption("ICE (+500원)")), 5000), // 4500+500
                    CartMenu("카페라떼", 2, listOf(CartOption("HOT"), CartOption("샷 추가 (+500원)")), 10500), // (5000+500)*2
                )
            ),
            // CartStore(
            //     storeName = "베이커리",
            //     cartMenus = listOf(
            //         CartMenu("크루아상", 3, emptyList(), 9000),
            //     )
            // )
        )
    )

    ShoppingCartScreenContent(
        uiState = dummyState,
        onBackClick = {},
        onCheckoutClick = {}
    )
}