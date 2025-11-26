package com.example.swa2502.presentation.ui.queue.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swa2502.R

@Composable
fun RestaurantInfoItem(
    modifier: Modifier = Modifier,
    restaurantId: String,
    restaurantState: String,
    restaurantName: String,
    waitingTime: Int,
    onNavigateToRestaurantQueue: () -> Unit,
) {
    val stateColor = when (restaurantState) {
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
                    text = restaurantState,
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
            text = restaurantName,
            style = TextStyle(
                color = Color(0xFF000000),
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold))
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            text = "예상 대기 시간 : ${waitingTime}분",
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
                onNavigateToRestaurantQueue()
            },
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF874A),
                contentColor = Color.White,
            ),
        ) {
            Text(
                text = "가게 조회",
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
private fun RestaurantInfoItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        RestaurantInfoItem(
            restaurantId = "111",
            restaurantState = "여유",
            restaurantName = "학생식당",
            waitingTime = 5,
            onNavigateToRestaurantQueue = {}
        )
        Spacer(modifier = Modifier.size(20.dp))
        RestaurantInfoItem(
            restaurantId = "222",
            restaurantState = "조금 혼잡",
            restaurantName = "도서관 식당",
            waitingTime = 10,
            onNavigateToRestaurantQueue = {}
        )
        Spacer(modifier = Modifier.size(20.dp))
        RestaurantInfoItem(
            restaurantId = "333",
            restaurantState = "혼잡",
            restaurantName = "식당3",
            waitingTime = 20,
            onNavigateToRestaurantQueue = {}
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}
