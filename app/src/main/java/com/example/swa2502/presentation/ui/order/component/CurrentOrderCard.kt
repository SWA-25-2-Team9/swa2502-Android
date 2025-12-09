package com.example.swa2502.presentation.ui.order.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swa2502.R
import com.example.swa2502.domain.model.CurrentOrderInfo
import com.example.swa2502.domain.model.OrderItem
import java.text.NumberFormat
import java.util.Locale

// 폰트 및 색상 정의
val PretendardBold = FontFamily(Font(R.font.pretendard_bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular))
val DividerGray = Color(0xFFE0E0E0)
val TextGray = Color(0xFF757575)

@Composable
fun CurrentOrderCard(orderInfo: CurrentOrderInfo) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            // 1. 가게 이름 및 주문 번호
            CardHeader(orderInfo)
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DividerGray)
            Spacer(modifier = Modifier.height(12.dp))


            // 2. 주문 메뉴 요약 (메뉴 및 옵션)
            OrderSummarySection(orderInfo)
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = DividerGray)

            // 3. 주문 상세 (총 가격 및 주문 시간)
            OrderDetailsSection(orderInfo)
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = DividerGray)

            // 4. 내 순서
            MyTurn(orderInfo)

        }
    }
}

@Composable
private fun CardHeader(orderInfo: CurrentOrderInfo) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = orderInfo.shopName,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
        )
        Text(
            text = "주문 번호: ${orderInfo.orderNumber}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun OrderSummarySection(orderInfo: CurrentOrderInfo) {
    // 모든 메뉴 항목 표시
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        orderInfo.items.forEachIndexed { index, item ->
            // 메뉴 이름과 가격
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${item.menuName} x${item.quantity}",
                    style = TextStyle(fontSize = 16.sp, fontFamily = PretendardSemiBold, color = Color.Black)
                )
                Text(
                    text = "${item.price}원",
                    style = TextStyle(fontSize = 16.sp, fontFamily = PretendardSemiBold, color = Color.Black)
                )
            }
            // 옵션 목록
            item.options.forEach { option ->
                Text(
                    text = "-$option",
                    style = TextStyle(fontSize = 14.sp, fontFamily = PretendardRegular, color = TextGray),
                    modifier = Modifier.padding(start = 5.dp, top = 2.dp)
                )
            }
            // 마지막 항목이 아니면 구분선 추가
            if (index < orderInfo.items.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = DividerGray, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun OrderDetailsSection(orderInfo: CurrentOrderInfo) {
    val formatter = NumberFormat.getNumberInstance(Locale.KOREA)
    val formattedTotalPrice = formatter.format(orderInfo.totalPrice)

    // 주문 시간 파싱 (시:분만 표시)
//    val orderedTime = try {
//        orderInfo.orderedAt.substringAfter('T').substringBeforeLast(':')
//    } catch (e: Exception) {
//        orderInfo.orderedAt
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        // 총 주문 금액
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "총 주문 금액",
                style = TextStyle(fontSize = 16.sp, fontFamily = PretendardSemiBold, color = Color.Black)
            )
            Text(
                text = "${formattedTotalPrice}원",
                style = TextStyle(fontSize = 16.sp, fontFamily = PretendardBold, color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // 주문 시간
//        Text(
//            text = "주문 시간: $orderedTime",
//            style = TextStyle(fontSize = 14.sp, fontFamily = PretendardRegular, color = TextGray)
//        )
    }
}

@Composable
private fun MyTurn(orderInfo: CurrentOrderInfo){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "내 순서: ${orderInfo.myTurn}",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFFF5722),
        )
    }
}

//
@Preview(showBackground = true)
@Composable
private fun CurrentOrderCardPreview() {
    Column(Modifier.padding(16.dp)) {
        val dummyOrder = CurrentOrderInfo(
            orderId = 2,
            orderNumber = "1번",
            shopName = "바비든든",
            myTurn = 2, // '메뉴 준비 중' 상태
            etaMinutes = 15,
            estimatedWaitTime = "약 15분 예상",
            totalPrice = 15000,
            orderedAt = "2025-12-04T12:47:21.000Z",
            items = listOf(
                OrderItem(menuName = "고기덮밥", quantity = 3, price = 5000, options = listOf("밥 추가", "덜 맵게")),
                OrderItem(menuName = "김치찌개", quantity = 1, price = 7000, options = emptyList())
            )
        )
        CurrentOrderCard(orderInfo = dummyOrder)
    }
}