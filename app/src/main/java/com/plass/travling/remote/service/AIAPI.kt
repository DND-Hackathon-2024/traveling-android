package com.plass.travling.remote.service

import retrofit2.http.POST
import retrofit2.http.Path

interface AIAPI {

    @POST("/chatgpt/{s}")
    suspend fun get(
        @Path("s") s: String
    ): AIResponse

}

data class AIResponse(
    val massage: String
)