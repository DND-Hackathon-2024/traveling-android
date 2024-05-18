package com.plass.travling.remote.response

import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("couponId")
    val couponId: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("couponName")
    val couponName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("couponDiscount")
    val couponDiscount: String,
    @SerializedName("couponCreateUserName")
    val couponCreateUserName: String,
    @SerializedName("trapId")
    val trapId: Int

)