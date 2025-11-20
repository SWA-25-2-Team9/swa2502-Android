package com.example.swa2502.presentation.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp)),
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            placeholder?.invoke()
        },
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF7F7F9),
            focusedTextColor = Color(0xFF333333),
            unfocusedContainerColor = Color(0xFFF7F7F9),
            unfocusedTextColor = Color(0xFF333333),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedPlaceholderColor = Color(0x99333333),
            unfocusedPlaceholderColor = Color(0x99333333),
        ),
    )
}

@Preview
@Composable
private fun CustomTextFieldPreview(){
    var text by rememberSaveable { mutableStateOf("") }

    CustomTextField(
        modifier = Modifier.fillMaxWidth(), // 크기 지정 필요한 경우 Modifier를 통해 지정(예: fillMaxWidth(), size() 등)
        text = text,
        onValueChange = {
            text = it
        },
        placeholder = {
            Text(
                text = "placeholder",
            )
        }
    )
}
