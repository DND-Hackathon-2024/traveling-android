package com.plass.travling.remote.request

import com.google.gson.annotations.SerializedName

data class JoinRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role")
    val role: String = "USER"
)
