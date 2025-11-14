package com.example.swa2502.presentation.navigation

sealed class Route(val route: String) {
    // 로그인, 회원가입
    object Login: Route(route = "login")
    object SighUp: Route(route = "signUp")

    // 관리자 메인
    object ManageQueue: Route(route = "manageRoute")

    // 사용자 메인
    object ShopOverview: Route(route = "shopOverview")

    // 주문 관련
    object OrderMenu: Route(route = "orderMenu")
    object MenuOption: Route(route = "menuOption")
    object ShoppingCart: Route("shoppingCart")

    // 결제 관련
    object Pay: Route(route = "pay")
    object PayResult: Route(route = "payResult")

    // 혼잡도 조회
    object RestaurantQueue: Route(route = "restaurantQueue")
    object RestaurantQueueDetail: Route(route = "restaurantQueueDetail")

    // 마이페이지
    object MyPage: Route(route = "myPage")
}