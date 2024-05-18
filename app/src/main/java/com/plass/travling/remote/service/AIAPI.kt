package com.plass.travling.remote.service

import com.plass.travling.remote.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AIAPI {

    @POST("/chatgpt/{s}")
    suspend fun get(
        @Path("s")s: String
    ): AIResponse

}

data class AIResponse(
    val message: String
)