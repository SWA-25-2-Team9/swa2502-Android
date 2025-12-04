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
import com.example.swa2502.domain.model.RestaurantInfo
import com.example.swa2502.presentation.ui.queue.component.RestaurantInfoItem
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueUiState
import com.example.swa2502.presentation.viewmodel.queue.RestaurantQueueViewModel

@Composable
fun RestaurantQueueScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit
) {
    val viewModel: RestaurantQueueViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RestaurantQueueScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onNavigateToDetail = onNavigateToDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantQueueScreenContent(
    modifier: Modifier = Modifier,
    uiState: RestaurantQueueUiState,
    onNavigateToDetail: (String) -> Unit
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
            uiState.restaurantList.forEach { restaurant ->
                RestaurantInfoItem(
                    modifier = Modifier,
                    restaurantId = restaurant.restaurantId,
                    restaurantState = restaurant.restaurantState,
                    restaurantName = restaurant.restaurantName,
                    occupiedSeats = restaurant.occupiedSeats,
                    totalSeats = restaurant.totalSeats,
                ) {
                    onNavigateToDetail(restaurant.restaurantId)
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RestaurantQueueScreenContentPreview() {
    RestaurantQueueScreenContent(
        modifier = Modifier,
        uiState = RestaurantQueueUiState(
            restaurantList = listOf(
                RestaurantInfo(
                    restaurantId = "111",
                    restaurantState = "여유",
                    restaurantName = "학생식당",
                    occupiedSeats = 10,
                    totalSeats = 100,
                ),
                RestaurantInfo(
                    restaurantId = "222",
                    restaurantState = "조금 혼잡",
                    restaurantName = "도서관 식당",
                    occupiedSeats = 60,
                    totalSeats = 100,
                ),
                RestaurantInfo(
                    restaurantId = "333",
                    restaurantState = "혼잡",
                    restaurantName = "식당3",
                    occupiedSeats = 80,
                    totalSeats = 100,
                ),
            )
        ),
        onNavigateToDetail = {}
    )
}
