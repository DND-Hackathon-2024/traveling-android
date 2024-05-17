package com.plass.travling.remote.service

import com.plass.travling.remote.response.BaseResponse
import com.plass.travling.remote.response.PlaceResponse
import retrofit2.http.GET

interface PlaceApi {

    @GET("/trap/list")
    suspend fun getPlaceList(): BaseResponse<List<PlaceResponse>>
}