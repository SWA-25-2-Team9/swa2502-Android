package com.example.swa2502.presentation.ui.queue.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
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
import com.example.swa2502.R

@Composable
fun ShopQueueDetailItem(
    modifier: Modifier = Modifier,
    shopId: String,
    shopState: String,
    shopName: String,
    waitingTime: Int,
    ordersDone: List<Int>,
    ordersInProgress: List<Int>
) {
    val stateColor = when (shopState) {
        "여유" -> Color(0xFF50C878)
        "조금 혼잡" -> Color(0xFFFF874A)
        "혼잡" -> Color(0xFFFF0000)
        else -> Color.Transparent
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0xFFDBDBDB), shape = RoundedCornerShape(8.dp))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
            ) {
                Box(
                    modifier = modifier
                        .size(12.dp)
                        .background(color = stateColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    modifier = Modifier,
                    text = shopState,
                    style = TextStyle(
                        color = Color(0xFF777777),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                    )
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            text = shopName,
            style = TextStyle(
                color = Color(0xFF000000),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold))
            )
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier,
            text = "예상 대기 시간 : ${waitingTime}분",
            style = TextStyle(
                color = Color(0xFF777777),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium))
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        HorizontalDivider(color = Color(0xFF777777), thickness = 1.dp)
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            modifier = Modifier,
            text = "조리 완료",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium))
            )
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            ordersDone.forEach { order ->
                SuggestionChip(
                    onClick = { },
                    enabled = false,
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color(0xFFFF874A),
                        labelColor = Color.White,
                        disabledContainerColor = Color(0xFFFF874A),
                        disabledLabelColor = Color.White,
                    ),
                    border = BorderStroke(0.dp, Color.Transparent),
                    label = {
                        Text(
                            text = "$order",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                            )
                        )
                    },
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            modifier = Modifier,
            text = "조리중",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium))
            )
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            ordersInProgress.forEach { order ->
                SuggestionChip(
                    onClick = { },
                    enabled = false,
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color(0xFFFF874A),
                        labelColor = Color.White,
                        disabledContainerColor = Color(0xFFFF874A),
                        disabledLabelColor = Color.White,
                    ),
                    border = BorderStroke(0.dp, Color.Transparent),
                    label = {
                        Text(
                            text = "$order",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                            )
                        )
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopQueueDetailItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        ShopQueueDetailItem(
            shopId = "111",
            shopState = "조금 혼잡",
            shopName = "학생식당",
            waitingTime = 10,
            ordersDone = listOf(123, 234, 345),
            ordersInProgress = listOf(456, 567, 678, 789, 890, 900)
        )
        Spacer(modifier = Modifier.size(20.dp))
        ShopQueueDetailItem(
            shopId = "222",
            shopState = "여유",
            shopName = "도서관 식당",
            waitingTime = 2,
            ordersDone = listOf(111, 222),
            ordersInProgress = listOf(333, 444, 555)
        )
        Spacer(modifier = Modifier.size(20.dp))
        ShopQueueDetailItem(
            shopId = "333",
            shopState = "혼잡",
            shopName = "식당3",
            waitingTime = 20,
            ordersDone = listOf(111, 222),
            ordersInProgress = listOf(333, 444, 555)
        )
    }
}