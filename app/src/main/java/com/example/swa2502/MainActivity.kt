package com.example.swa2502

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.swa2502.presentation.navigation.BottomNavItem
import com.example.swa2502.presentation.navigation.MainNavGraph
import com.example.swa2502.presentation.navigation.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBarItems = listOf(
                BottomNavItem(
                    label = "홈",
                    route = Route.ShopOverview.route,
                    icon = R.drawable.ic_bottomnav_home
                ),
                BottomNavItem(
                    label = "주문조회",
                    route = Route.MyOrder.route,
                    icon = R.drawable.ic_bottomnav_myorder
                ),
                BottomNavItem(
                    label = "혼잡도",
                    route = Route.RestaurantQueue.route,
                    icon = R.drawable.ic_bottomnav_queue
                ),
                BottomNavItem(
                    label = "마이페이지",
                    route = Route.MyPage.route,
                    icon = R.drawable.ic_bottomnav_mypage
                )
            )
            //BottomNavigation을 보여줄 화면들
            val bottomNavRoutes = listOf(
                Route.ShopOverview.route,
                Route.MyOrder.route,
                Route.RestaurantQueue.route,
                Route.MyPage.route
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                bottomBar = {
                    if (currentRoute in bottomNavRoutes) {
                        NavigationBar(
                            modifier = Modifier
                                .drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    drawLine(
                                        color = Color(0xFFF5F5F5), // NavigationBar의 상단 테두리
                                        start = Offset(0f, 0f),
                                        end = Offset(size.width, 0f),
                                        strokeWidth = strokeWidth,
                                    )
                                },
                            containerColor = Color.White
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination

                            navBarItems.forEach { item ->
                                val selected =
                                    currentDestination?.hierarchy?.any { it.route == item.route } == true
                                NavigationBarItem(
                                    icon = {
                                        Box(
                                            modifier = Modifier.size(38.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(id = item.icon),
                                                contentDescription = item.label,
                                            )
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = item.label,
                                        )
                                    },
                                    selected = selected,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            // Home 화면까지 모든 백스택 제거 (Bottom Nav의 첫 화면)
                                            popUpTo(Route.ShopOverview.route) {
                                                inclusive = false
                                                saveState = true
                                            }
                                            // 같은 화면이 스택에 있으면 재사용
                                            launchSingleTop = true
                                            // 이전 상태 복원
                                            restoreState = true
                                        }
                                    },
                                    colors =
                                        NavigationBarItemDefaults.colors(
                                            indicatorColor = Color.Transparent,
                                            selectedIconColor = Color(0xFFFF874A),
                                            selectedTextColor = Color(0xFFFF874A),
                                            unselectedIconColor = Color(0xFF9Fa5B0),
                                            unselectedTextColor = Color(0xFF9Fa5B0)
                                        )
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                MainNavGraph(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                )
            }
        }
    }
}
