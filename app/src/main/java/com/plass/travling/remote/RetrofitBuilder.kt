package com.plass.travling.remote

import com.plass.travling.remote.service.CouponAPI
import com.plass.travling.remote.service.MemberAPI
import com.plass.travling.remote.service.PlaceApi
import com.plass.travling.utiles.Env
import retrofit2.Retrofit

object RetrofitBuilder {
    private var retrofit: Retrofit? = null

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Env.BASE_URl)
                .build()
        }
        return retrofit!!
    }

    fun getMemberApi(): MemberAPI =
        getRetrofit().create(MemberAPI::class.java)

    fun getCouponApi(): CouponAPI =
        getRetrofit().create(CouponAPI::class.java)

    fun getPlaceApi(): PlaceApi =
        getRetrofit().create(PlaceApi::class.java)
}