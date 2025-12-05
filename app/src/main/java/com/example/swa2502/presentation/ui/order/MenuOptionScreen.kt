// 📂 presentation/ui/order/MenuOptionScreen.kt (수정)
package com.example.swa2502.presentation.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.presentation.viewmodel.order.MenuOptionUiState
import com.example.swa2502.presentation.viewmodel.order.MenuOptionViewModel
import com.example.swa2502.domain.model.OptionGroup // Domain 모델 임포트
import com.example.swa2502.domain.model.OptionItem // Domain 모델 임포트
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.swa2502.R
import com.example.swa2502.domain.model.MenuItem // MenuItem import
import com.example.swa2502.presentation.ui.order.component.DividerGray

// ----------------------------------------------------
// 1. 메인 화면 Composable
// ----------------------------------------------------
@Composable
fun MenuOptionScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {
    val viewModel: MenuOptionViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    MenuOptionScreenContent(
        modifier = modifier,
        uiState = uiState,
        onBackClick = onBackClick,
        onOptionSelected = viewModel::onOptionSelected,
        onCartClick = onBackClick,
//        onAddToCartClick = onAddToCartClick
    )
}

// ----------------------------------------------------
// 2. 화면 내용 Composable
// ----------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuOptionScreenContent(
    modifier: Modifier = Modifier,
    uiState: MenuOptionUiState,
    onBackClick: () -> Unit,
    onOptionSelected: (groupId: Int, optionId: Int) -> Unit,
    onCartClick: () -> Unit,
//    onAddToCartClick: () -> Unit,
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
                    text = "${uiState.menuName}",
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

    ) { paddingValues ->
        // 로딩 및 에러 처리
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
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // 옵션 그룹
                items(uiState.optionGroups) { group ->
                    OptionGroupItem(
                        optionGroup = group,
                        onOptionSelected = onOptionSelected
                    )
                }


            }
        }
    }
}


// 컴포넌트: 옵션 그룹 아이템
@Composable
private fun OptionGroupItem(
    optionGroup: OptionGroup,
    onOptionSelected: (groupId: Int, optionId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = optionGroup.name + if (optionGroup.isRequired) " (필수)" else " (선택)",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = DividerGray)
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

@Preview(showBackground = true, name = "메뉴 옵션 화면 Preview")
@Composable
private fun MenuOptionScreenContentPreview() {
    // 1. 더미 옵션 항목 정의
    val dummyOptionItems1 = listOf(
        OptionItem(101, "보통 맛", 0),
        OptionItem(102, "매운 맛 (+500원)", 500),
        OptionItem(103, "아주 매운 맛 (+1000원)", 1000)
    )

    val dummyOptionItems2 = listOf(
        OptionItem(201, "기본", 0),
        OptionItem(202, "고기 추가 (+500원)", 500),
        OptionItem(203, "계란 추가 (+300원)", 300)
    )

    // 2. 더미 옵션 그룹 정의
    val dummyOptionGroups = listOf(
        OptionGroup(
            id = 1,
            name = "맵기 선택",
            isRequired = true,
            options = dummyOptionItems1,
            selectedOptionId = dummyOptionItems1.first().id // 첫 번째 옵션 기본 선택
        ),
        OptionGroup(
            id = 2,
            name = "추가 옵션",
            isRequired = false,
            options = dummyOptionItems2,
            selectedOptionId = null // 기본 선택 없음
        )
    )

    // 3. 기본 가격 및 초기 총 금액 계산
    val basePrice = 4500
    val quantity = 1

    // 초기 총 금액 계산 (기본 가격 + 기본 선택 옵션 가격)
    val initialTotal = basePrice + (dummyOptionGroups.sumOf { group ->
        group.options.find { it.id == group.selectedOptionId }?.price ?: 0
    }) * quantity

    // 4. UI 상태 생성
    val previewUiState = MenuOptionUiState(
        isLoading = false,
        menuName = "제육덮밥",
        basePrice = basePrice,
        optionGroups = dummyOptionGroups,
        quantity = quantity,
        totalAmount = initialTotal, // 5000원 (4500 + 500)
        errorMessage = null,
    )

    // 5. Preview Content 호출
    MenuOptionScreenContent(
        modifier = Modifier,
        uiState = previewUiState,
        onBackClick = {},
        onCartClick = {},
        onOptionSelected = { _, _->}
    )
}