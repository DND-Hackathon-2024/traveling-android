package com.plass.travling.remote.request

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)
