package com.example.swa2502.domain.model

// 장바구니 가게 정보
data class CartStore(
    val storeId: Int,
    val storeName: String,
    val cartMenus: List<CartMenu>,
)

// 장바구니 메뉴 항목
data class CartMenu(
    val cartItemId: Int, // 장바구니 항목 ID (수량 변경, 삭제 시 사용)
    val menuId: Int,
    val menuName: String,
    val quantity: Int,
    val options: List<CartOption>,
    val totalPrice: Int,
    val storeId: Int,
)

// 장바구니 메뉴 옵션
data class CartOption(
    val name: String,
)