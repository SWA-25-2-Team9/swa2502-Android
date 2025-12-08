package com.example.swa2502.data.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//사용
@Serializable
data class CartStoreDto(
    @SerialName("storeId")
    val storeId: Int,
    @SerialName("storeName")
    val storeName: String,
    @SerialName("totalPrice")
    val totalPrice: Int,
    @SerialName("items")
    val items: List<CartMenuDto>,
)

@Serializable
data class CartMenuDto(
    @SerialName("cartItemId")
    val cartItemId: Int,
    @SerialName("menuId")
    val menuId: Int,
    @SerialName("menuName")
    val menuName: String,
    @SerialName("quantity")
    val quantity: Int,
    @SerialName("optionsText")
    val optionsText: String,
    @SerialName("price")
    val price: Int,
)