package com.example.swa2502.presentation.ui.shop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R
import com.example.swa2502.domain.model.ShopOverviewInfo
import com.example.swa2502.presentation.ui.shop.component.ShopOverviewItem
import com.example.swa2502.presentation.viewmodel.shop.ShopOverviewUiState
import com.example.swa2502.presentation.viewmodel.shop.ShopOverviewViewModel

@Composable
fun ShopOverviewScreen(
    modifier: Modifier = Modifier,
    onNavigateToOrder: () -> Unit,
) {
    val viewModel: ShopOverviewViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ShopOverviewScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
        onNavigateToOrder = onNavigateToOrder
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopOverviewScreenContent(
    modifier: Modifier = Modifier,
    uiState: ShopOverviewUiState,
    onNavigateToOrder: () -> Unit,
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    val dropdownList = uiState.restaurantList
    var selectedDropdownMenu by rememberSaveable { 
        mutableStateOf(dropdownList.firstOrNull() ?: "")
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier,
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "가게별 대기열",
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
        
        Spacer(modifier = Modifier.size(20.dp))
        
        // 드롭다운 메뉴
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { dropdownExpanded = !dropdownExpanded },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedDropdownMenu,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                    )
                )
                Spacer(modifier = Modifier.size(4.dp))
                Icon(
                    modifier = Modifier
                        .rotate(270f)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "dropdown icon",
                    tint = Color.Black
                )
            }
            
            DropdownMenu(
                expanded = dropdownExpanded,
                offset = DpOffset(0.dp, 0.dp),
                onDismissRequest = { dropdownExpanded = false }
            ) {
                dropdownList.forEachIndexed { index, selectionOption ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = selectionOption,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                                )
                            ) 
                        },
                        onClick = {
                            selectedDropdownMenu = dropdownList[index]
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.size(20.dp))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(state = scrollState)
        ) {
            uiState.shopInfoList.forEach { shopInfo ->
                ShopOverviewItem(
                    modifier = Modifier,
                    shopId = shopInfo.shopId,
                    shopState = shopInfo.shopState,
                    shopName = shopInfo.shopName,
                    queueSize = shopInfo.queueSize,
                    onNavigateToOrder = onNavigateToOrder
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopOverviewScreenContentPreview() {
    ShopOverviewScreenContent(
        modifier = Modifier,
        uiState = ShopOverviewUiState(
            shopInfoList = listOf(
                ShopOverviewInfo(
                    shopId = "111",
                    shopState = "여유",
                    shopName = "바비든든",
                    queueSize = 3,
                ),
                ShopOverviewInfo(
                    shopId = "222",
                    shopState = "조금 혼잡",
                    shopName = "폭풍분식",
                    queueSize = 50,
                ),
                ShopOverviewInfo(
                    shopId = "333",
                    shopState = "혼잡",
                    shopName = "경성돈카츠",
                    queueSize = 100,
                ),
            )
        ),
        onNavigateToOrder = {}
    )
}
