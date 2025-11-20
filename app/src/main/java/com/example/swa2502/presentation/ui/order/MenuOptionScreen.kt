package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.MenuOptionUiState
import com.example.swa2502.presentation.viewmodel.order.MenuOptionViewModel
import com.example.swa2502.presentation.viewmodel.order.OptionGroup
import com.example.swa2502.presentation.viewmodel.order.OptionItem
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.swa2502.R

// ----------------------------------------------------
// 1. 메인 화면 Composable
// ----------------------------------------------------
@Composable
fun MenuOptionScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onCartClick: () -> Unit,
) {
    val viewModel: MenuOptionViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MenuOptionScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onBackClick = onBackClick,
        onAddToCartClick = onAddToCartClick,
        onCartClick = onCartClick,
        onOptionSelected = viewModel::onOptionSelected // ViewModel 함수 연결
    )
}

// ----------------------------------------------------
// 2. Content Composable (모든 UI 요소 포함)
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionScreenContent(
    modifier: Modifier = Modifier,
    uiState: MenuOptionUiState,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onCartClick: () -> Unit,
    onOptionSelected: (groupId: Int, optionId: Int) -> Unit,
) {

    // BottomBar
//    @Composable
//    fun MenuOptionBottomBar() {
//        Button(
//            onClick = onAddToCartClick,
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // #FF9800
//            shape = RoundedCornerShape(0.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//        ) {
//            Text(
//                text = "${String.format("%,d", uiState.totalAmount)}원 카트에 담기",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//        }
//    }

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

                // 메뉴 이름
                Text(
                    text = uiState.menuName,
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
                onClick = onAddToCartClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // #FF9800
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "${String.format("%,d", uiState.totalAmount)}원 카트에 담기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        modifier = modifier.background(Color(0xFFF5F5F5)) // 배경색 #F5F5F5
    ) { paddingValues ->
        // 옵션 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            item {
                // 3. 옵션 그룹 목록
                uiState.optionGroups.forEach { group ->
                    // OptionGroupSection Composable은 별도 파일 또는 이 파일 하단에 정의되어야 합니다.
                    // 현재는 더미 호출을 사용합니다.
                    OptionGroupSection(
                        optionGroup = group,
                        onOptionSelected = onOptionSelected
                    )
                }
            }
        }
    }
}


// ----------------------------------------------------
// 3. Preview Composable
// ----------------------------------------------------
@Preview(showBackground = true)
@Composable
private fun MenuOptionScreenContentPreview() {
    val dummyState = MenuOptionUiState(
        menuName = "아메리카노",
        basePrice = 4500,
        optionGroups = listOf(
            OptionGroup(1, "온도", true, listOf(OptionItem(101, "HOT", 0), OptionItem(102, "ICE (+500원)", 500)), 101),
            OptionGroup(2, "사이즈", false, listOf(OptionItem(201, "Large (+500원)", 500), OptionItem(202, "Extra Large (+1000원)", 1000)), 201)
        ),
        totalAmount = 5000 // 예시 가격 (4500 + 500)
    )

    // Preview에서는 실제 navigation이나 ViewModel 호출 없이 더미 함수를 사용합니다.
    MenuOptionScreenContent(
        uiState = dummyState,
        onBackClick = {},
        onAddToCartClick = {},
        onCartClick = {},
        onOptionSelected = { _, _ -> }
    )
}

// (OptionGroupSection 및 기타 UI 보조 함수들은 이 파일 하단이나 별도 파일에 존재해야 합니다.
//  요청에 따라 OptionGroupSection의 세부 구현은 생략하고 구조만 맞춥니다.)
@Composable
fun OptionGroupSection(
    optionGroup: OptionGroup,
    onOptionSelected: (groupId: Int, optionId: Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // LazyColumn의 8dp 패딩에 맞춰 Card 내부 간격 조절
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = optionGroup.name + if (optionGroup.isRequired) " (필수)" else " (선택)",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            optionGroup.options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOptionSelected(optionGroup.id, option.id) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option.id == optionGroup.selectedOptionId,
                        onClick = { onOptionSelected(optionGroup.id, option.id) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF9800))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option.name,
                        fontSize = 15.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (option.price > 0) {
                        Text(text = "+${String.format("%,d", option.price)}원", fontSize = 15.sp, color = Color(0xFF777777))
                    }
                }
            }
        }
    }
}