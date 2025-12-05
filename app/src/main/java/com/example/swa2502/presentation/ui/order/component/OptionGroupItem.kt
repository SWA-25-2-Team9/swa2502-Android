// 📂 presentation/ui/order/component/OptionGroupItem.kt
package com.example.swa2502.presentation.ui.order.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swa2502.domain.model.OptionGroup
import com.example.swa2502.domain.model.OptionItem


@Composable
fun OptionGroupItem(
    optionGroup: OptionGroup,
    onOptionSelected: (groupId: Int, optionId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        // LazyColumn에서 사용할 것을 고려하여 상하좌우 패딩을 조정
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                        // 전체 Row 클릭 가능하게 설정
                        .clickable { onOptionSelected(optionGroup.id, option.id) }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option.id == optionGroup.selectedOptionId,
                        // 라디오 버튼 클릭 시에도 onOptionSelected 호출
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
                        Text(
                            text = "+${String.format("%,d", option.price)}원",
                            fontSize = 15.sp,
                            color = Color(0xFF777777)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "필수 옵션 그룹 미리보기")
@Composable
private fun OptionGroupItemRequiredPreview() {
    val dummyOptionItems1 = listOf(
        OptionItem(101, "보통 맛", 0),
        OptionItem(102, "매운 맛 (+500원)", 500),
        OptionItem(103, "아주 매운 맛 (+1000원)", 1000)
    )
    val dummyOptionGroup = OptionGroup(
        id = 1,
        name = "맵기 선택",
        isRequired = true,
        options = dummyOptionItems1,
        selectedOptionId = 101 // 선택됨
    )
    OptionGroupItem(optionGroup = dummyOptionGroup, onOptionSelected = { _, _ -> })
}

@Preview(showBackground = true, name = "선택 옵션 그룹 미리보기")
@Composable
private fun OptionGroupItemOptionalPreview() {
    val dummyOptionItems2 = listOf(
        OptionItem(201, "기본", 0),
        OptionItem(202, "Extra Shot (+500원)", 500),
        OptionItem(203, "시럽 추가 (+300원)", 300)
    )
    val dummyOptionGroup = OptionGroup(
        id = 2,
        name = "추가 옵션",
        isRequired = false,
        options = dummyOptionItems2,
        selectedOptionId = 202 // 선택됨
    )
    OptionGroupItem(optionGroup = dummyOptionGroup, onOptionSelected = { _, _ -> })
}