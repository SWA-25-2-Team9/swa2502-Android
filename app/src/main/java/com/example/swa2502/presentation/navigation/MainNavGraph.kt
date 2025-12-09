package com.example.swa2502.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swa2502.presentation.ui.auth.LoginScreen
import com.example.swa2502.presentation.ui.auth.SignUpScreen
import com.example.swa2502.presentation.ui.manage.ManageQueueScreen
import com.example.swa2502.presentation.ui.mypage.MyPageScreen
import com.example.swa2502.presentation.ui.order.MenuOptionScreen
import com.example.swa2502.presentation.ui.order.MyOrderScreen
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
            LoginScreen(
                modifier = modifier,
                onNavigateToUserMain = {
                    navController.navigate(Route.ShopOverview.route){
                        popUpTo(Route.Login.route){
                            inclusive = true
                        }
                    }
                },
                onNavigateToAdminMain = {
                    navController.navigate(Route.ManageQueue.route){
                        popUpTo(Route.Login.route){
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Route.SighUp.route)
                }
            )
        }
        composable(route = Route.SighUp.route){
            SignUpScreen(
                modifier = modifier
            )
        }
        /* 관리자 */
        composable(route = Route.ManageQueue.route){
            ManageQueueScreen(
                modifier = modifier
            )
        }

        /* 사용자 */
        composable(route = Route.ShopOverview.route){
            ShopOverviewScreen(
                modifier = modifier,
                onNavigateToOrder = { shopId, shopName ->
                    navController.navigate(Route.OrderMenu.createRoute(shopId, shopName))
                }
            )
        }

        /* 주문 */
        composable(
            route = Route.OrderMenu.route,
            arguments = listOf(
                navArgument("shopId") { type = NavType.StringType },
                navArgument("shopName") { type = NavType.StringType },
            )
        ){
            OrderMenuScreen(
                modifier = modifier,
                onBackClick = {
                    navController.navigate(Route.ShopOverview.route)
                },
                onCartClick = {
                    navController.navigate(Route.ShoppingCart.route)
                },
                onNavigateToMenuOption = { menuItem ->
                    navController.navigate(Route.MenuOption.createRoute(menuItem.menuId))
                }
            )
        }

        composable(
            route = Route.MenuOption.route,
            arguments = listOf(
                navArgument("menuId") { type = NavType.IntType }
            )
        ){
            MenuOptionScreen(
                modifier = modifier,
                onBackClick = { navController.navigateUp() },
                onCartClick = { navController.navigate(Route.ShoppingCart.route) },
                onAddToCartClick = {
                    navController.navigateUp() // OrderMenu에 전달된 shopId/shopName 인자 보존
                }
            )
        }

        composable(route = Route.ShoppingCart.route){
            ShoppingCartScreen(
                modifier = modifier,
                onBackClick =  { navController.navigateUp() },
                onCheckoutClick = { navController.navigate(Route.Pay.route) }
            )
        }

        composable(route = Route.MyOrder.route){
            MyOrderScreen(
                modifier = modifier,
                onBackClick = { navController.navigateUp() },
                onCartClick = { navController.navigate(Route.ShoppingCart.route) }
            )
        }

        /* 결제 */
        composable(route = Route.Pay.route){
            PayScreen(
                modifier = modifier,
                onPayClick = {navController.navigate(Route.MyOrder.route)},
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(route = Route.PayResult.route){
            PayResultScreen(
                modifier = modifier
            )
        }

        /* 혼잡도 */
        composable(route = Route.RestaurantQueue.route){
            RestaurantQueueScreen(
                modifier = modifier,
                onNavigateToDetail = { restaurantId ->
                    navController.navigate(Route.RestaurantQueueDetail.createRoute(restaurantId))
                }
            )
        }
        composable(
            route = Route.RestaurantQueueDetail.route,
            arguments = listOf(
                navArgument("restaurantId") { type = NavType.StringType }
            )
        ){
            RestaurantQueueDetailScreen(
                modifier = modifier,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        /* 마이페이지 */
        composable(route = Route.MyPage.route){
            MyPageScreen(
                modifier = modifier
            )
        }
    }
}