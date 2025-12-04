package com.example.swa2502.presentation.ui.order.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swa2502.domain.model.MenuItem // MenuItem import

@Composable
fun MenuItemRow(
    menuItem: MenuItem,
    onClick: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // soldout시 클릭 비활성화
    val clickableModifier = if (!menuItem.isSoldOut) {
        Modifier.clickable(onClick = { onClick(menuItem) })
    } else Modifier

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable(onClick = { onClick(menuItem) }) // 클릭 시 메뉴 옵션 화면으로 이동
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = menuItem.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${String.format("%,d", menuItem.price)}원",
                    fontSize = 14.sp,
                    color = Color(0xFF555555)
                )
                // 품절 표시 (isSoldOut이 true일 경우)
                if (menuItem.isSoldOut) {
                    Text(
                        text = "품절",
                        fontSize = 14.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top=4.dp)
                    )
                }
            }

            // 메뉴 이미지
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFD3D3D3)) // #D3D3D3
            ) {
                Text(
                    text = "메뉴 이미지",
                    color = Color(0xFF777777), // #777777
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}