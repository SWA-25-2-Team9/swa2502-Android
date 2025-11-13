package com.example.swa2502.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ){
        /* 로그인, 회원가입 */

        /* 관리자 */

        /* 사용자 */

        /* 주문 */

        /* 혼잡도 */

        /* 마이페이지 */
    }
}