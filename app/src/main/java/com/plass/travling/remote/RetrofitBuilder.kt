package com.plass.travling.remote

import com.plass.travling.remote.interceptor.TokenInterceptor
import com.plass.travling.remote.service.CouponAPI
import com.plass.travling.remote.service.MemberAPI
import com.plass.travling.remote.service.PlaceApi
import com.plass.travling.utiles.Env
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitBuilder {
    private var retrofit: Retrofit? = null
    private var noTokenRetrofit: Retrofit? = null

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val okhttpBuilder = OkHttpClient().newBuilder()
                .addInterceptor(TokenInterceptor())
            retrofit = Retrofit.Builder()
                .baseUrl(Env.BASE_URl)
                .client(okhttpBuilder.build())
                .build()
        }
        return retrofit!!
    }

    private fun getNoTokenRetrofit(): Retrofit {
        if (noTokenRetrofit == null) {
            noTokenRetrofit = Retrofit.Builder()
                .baseUrl(Env.BASE_URl)
                .build()
        }
        return noTokenRetrofit!!
    }

    fun getMemberApi(): MemberAPI =
        getNoTokenRetrofit().create(MemberAPI::class.java)

    fun getCouponApi(): CouponAPI =
        getRetrofit().create(CouponAPI::class.java)

    fun getPlaceApi(): PlaceApi =
        getRetrofit().create(PlaceApi::class.java)
}