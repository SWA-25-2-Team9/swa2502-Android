package com.example.swa2502.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swa2502.presentation.ui.auth.LoginScreen
import com.example.swa2502.presentation.ui.auth.SignUpScreen
import com.example.swa2502.presentation.ui.manage.ManageQueueScreen
import com.example.swa2502.presentation.ui.mypage.MyPageScreen
import com.example.swa2502.presentation.ui.order.MenuOptionScreen
import com.example.swa2502.presentation.ui.order.OrderMenuScreen
import com.example.swa2502.presentation.ui.order.ShoppingCartScreen
import com.example.swa2502.presentation.ui.pay.PayResultScreen
import com.example.swa2502.presentation.ui.pay.PayScreen
import com.example.swa2502.presentation.ui.queue.RestaurantQueueDetailScreen
import com.example.swa2502.presentation.ui.queue.RestaurantQueueScreen
import com.example.swa2502.presentation.ui.shop.ShopOverviewScreen

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
        composable(route = Route.Login.route){
            LoginScreen()
        }
        composable(route = Route.SighUp.route){
            SignUpScreen()
        }
        /* 관리자 */
        composable(route = Route.ManageQueue.route){
            ManageQueueScreen()
        }

        /* 사용자 */
        composable(route = Route.ShopOverview.route){
            ShopOverviewScreen()
        }

        /* 주문 */
        composable(route = Route.OrderMenu.route){
            OrderMenuScreen()
        }

        composable(route = Route.MenuOption.route){
            MenuOptionScreen()
        }

        composable(route = Route.ShoppingCart.route){
            ShoppingCartScreen()
        }

        /* 결제 */
        composable(route = Route.Pay.route){
            PayScreen()
        }

        composable(route = Route.PayResult.route){
            PayResultScreen()
        }

        /* 혼잡도 */
        composable(route = Route.RestaurantQueue.route){
            RestaurantQueueScreen()
        }
        composable(route = Route.RestaurantQueueDetail.route){
            RestaurantQueueDetailScreen()
        }

        /* 마이페이지 */
        composable(route = Route.MyPage.route){
            MyPageScreen()
        }
    }
}