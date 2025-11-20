package com.example.swa2502.presentation.ui.manage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swa2502.R

@Composable
fun ManageQueueItem(
    modifier: Modifier = Modifier,
    orderId: String,
    orderState: String,
    orderTime: String,
    orderNumber: Int,
    menus: List<String>,
    onStateChange: () -> Unit,
) {
    val stateColor = when (orderState) {
        "조리 완료" -> Color(0xFF50C878)
        "조리중" -> Color(0xFFFF0000)
        else -> Color.Transparent
    }
    val stateString = when (orderState){
        "조리 완료" -> "수령 완료"
        "조리중" -> "조리 완료"
        else -> ""
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
            ) {
                Box(
                    modifier = modifier
                        .size(12.dp)
                        .background(color = stateColor, shape =  CircleShape)
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    modifier = Modifier,
                    text = orderState,
                    style = TextStyle(
                        color = Color(0xFF777777),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                    )
                )
            }
            Text(
                modifier = Modifier,
                text = "$orderTime 주문",
                style = TextStyle(
                    color = Color(0xFF777777),
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                )
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            text = "${orderNumber}번",
            style = TextStyle(
                color = Color(0xFF000000),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold))
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            text = "메뉴 : ${menus.joinToString(", ")}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                color = Color(0xFF777777),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium))
            )
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = {
                // TODO: 로직 구현 예정
                onStateChange()
            },
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = stateColor,
                contentColor = Color.White,
            ),
        ) {
            Text(
                text = stateString,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold))
                ),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManageQueueItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        ManageQueueItem(
            modifier = Modifier,
            orderId = "abc123",
            orderState = "조리 완료",
            orderTime = "12:30",
            orderNumber = 123,
            menus = listOf("메뉴1", "메뉴2", "메뉴3"),
        ) {

        }
        ManageQueueItem(
            modifier = Modifier,
            orderId = "abc321",
            orderState = "조리중",
            orderTime = "12:32",
            orderNumber = 321,
            menus = listOf("메뉴1", "메뉴2", "메뉴3"),
        ) {

        }
    }
}