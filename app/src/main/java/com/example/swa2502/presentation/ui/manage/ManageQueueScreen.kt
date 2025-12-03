package com.example.swa2502.presentation.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.swa2502.R
import com.example.swa2502.domain.model.Order
import com.example.swa2502.presentation.ui.manage.component.ManageQueueItem
import com.example.swa2502.presentation.viewmodel.manage.ManageQueueUiState
import com.example.swa2502.presentation.viewmodel.manage.ManageQueueViewModel

@Composable
fun ManageQueueScreen(modifier: Modifier = Modifier) {
    val viewModel: ManageQueueViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ManageQueueScreenContent(
        modifier = Modifier,
        uiState = uiState.value,
        onTabSelected = { tab -> viewModel.selectTab(tab) },
        onOrderStatusChange = { orderItemId -> viewModel.updateOrderStatus(orderItemId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageQueueScreenContent(
    modifier: Modifier = Modifier,
    uiState: ManageQueueUiState,
    onTabSelected: (String) -> Unit = {},
    onOrderStatusChange: (String) -> Unit = {},
) {
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
                        text = "주문 관리",
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
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable(onClick = {
                                onTabSelected("전체 주문")
                            }),
                        text = "전체 주문",
                        style = TextStyle(
                            color = if (uiState.selectedTab == "전체 주문") Color(0xFFFF874A) else Color(0xFF777777),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold))
                        )
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable(onClick = {
                                onTabSelected("조리중")
                            }),
                        text = "조리중",
                        style = TextStyle(
                            color = if (uiState.selectedTab == "조리중") Color(0xFFFF874A) else Color(0xFF777777),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold))
                        )
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable(onClick = {
                                onTabSelected("수령 대기")
                            }),
                        text = "수령 대기",
                        style = TextStyle(
                            color = if (uiState.selectedTab == "수령 대기") Color(0xFFFF874A) else Color(0xFF777777),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold))
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFF777777)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.orderList.forEach { order ->
                    ManageQueueItem(
                        modifier = Modifier,
                        orderId = order.id,
                        orderNumber = order.orderNumber,
                        orderState = order.orderState,
                        orderTime = order.timeStamp,
                        menus = order.menus,
                        onStateChange = {
                            onOrderStatusChange(order.id)
                        }
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageQueueScreenContentPreview() {
    ManageQueueScreenContent(
        modifier = Modifier,
        uiState = ManageQueueUiState(
            orderList =
                listOf(
                    Order(
                        id = "100",
                        orderNumber = "100",
                        orderState = "READY",
                        menus = listOf("라면"),
                        timeStamp = "12:00"
                    ),
                    Order(
                        id = "101",
                        orderNumber = "101",
                        orderState = "ACCEPTED",
                        menus = listOf("김밥", "떡볶이"),
                        timeStamp = "12:05"
                    ),
                    Order(
                        id = "102",
                        orderNumber = "102",
                        orderState = "ACCEPTED",
                        menus = listOf("돈까스"),
                        timeStamp = "12:10"
                    )
                ),
            selectedTab = "전체 주문"
        ),
    )
}
