package com.example.swa2502.presentation.ui.queue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R
import com.example.swa2502.domain.model.ShopQueueInfo
import com.example.swa2502.presentation.ui.queue.component.ShopQueueDetailItem
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueDetailUiState
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueDetailViewModel

@Composable
fun RestaurantQueueDetailScreen(modifier: Modifier = Modifier) {
    val viewModel: RestaurantQueueDetailViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RestaurantQueueDetailScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantQueueDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: RestaurantQueueDetailUiState,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
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
        Spacer(modifier = Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(state = scrollState)
        ) {
            uiState.shopQueueInfos.forEach { shopQueueInfo ->
                ShopQueueDetailItem(
                    modifier = Modifier,
                    shopId = shopQueueInfo.shopId,
                    shopState = shopQueueInfo.shopState,
                    shopName = shopQueueInfo.shopName,
                    ordersDone = shopQueueInfo.ordersDone,
                    ordersInProgress = shopQueueInfo.ordersInProgress
                )
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantQueueDetailScreenContentPreview() {
    RestaurantQueueDetailScreenContent(
        modifier = Modifier,
        uiState = RestaurantQueueDetailUiState(
            shopQueueInfos = listOf(
                ShopQueueInfo(
                    shopId = "111",
                    shopState = "조금 혼잡",
                    shopName = "학생식당",
                    ordersDone = listOf(123, 234, 345),
                    ordersInProgress = listOf(456, 567, 678, 789, 890, 900)
                ),
                ShopQueueInfo(
                    shopId = "222",
                    shopState = "여유",
                    shopName = "도서관 식당",
                    ordersDone = listOf(111, 222),
                    ordersInProgress = listOf(333, 444, 555)
                ),
                ShopQueueInfo(
                    shopId = "333",
                    shopState = "혼잡",
                    shopName = "식당3",
                    ordersDone = listOf(111, 222),
                    ordersInProgress = listOf(333, 444, 555)
                )
            )
        ),
    )
}
