package com.plass.travling.remote.response

data class PlaceResponse(
    val id: Int,
    val placeName: String,
    val placeDesc: String,
    val address: String,
    val couponId: Int,
    val imgUrl: String
)