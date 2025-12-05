package com.example.swa2502.data.dto.order

// API 응답 구조: List<CartStoreDto>
data class CartStoreDto(
    val storeId: Int,
    val storeName: String,
    val cartMenus: List<CartMenuDto>,
)

data class CartMenuDto(
    val menuId: Int, // 장바구니 항목 ID (API DELETE/PATCH Path에 사용되는 ID라고 가정)
    val menuName: String,
    val quantity: Int,
    val options: List<CartOptionDto>,
    val totalPrice: Int, // 이 메뉴와 옵션, 수량의 총 가격
    val storeId: Int,
)

data class CartOptionDto(
    val name: String,
    val price: Int, // 옵션 가격을 포함하여 매핑에 유용하도록 추가
)