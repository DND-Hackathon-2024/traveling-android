package com.plass.travling.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("httpStatus")
    val httpStatus: String,
    @SerializedName("massage")
    val massage: String,
    @SerializedName("data")
    val data: T
)

data class BaseVoidResponse(
    @SerializedName("httpStatus")
    val httpStatus: String,
    @SerializedName("massage")
    val massage: String,
)