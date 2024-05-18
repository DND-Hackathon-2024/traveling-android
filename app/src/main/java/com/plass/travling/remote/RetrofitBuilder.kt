package com.plass.travling.remote

import android.util.Log
import com.google.gson.Gson
import com.plass.travling.remote.Json.isJsonArray
import com.plass.travling.remote.Json.isJsonObject
import com.plass.travling.remote.interceptor.TokenInterceptor
import com.plass.travling.remote.service.AIAPI
import com.plass.travling.remote.service.CouponAPI
import com.plass.travling.remote.service.MemberAPI
import com.plass.travling.remote.service.PlaceApi
import com.plass.travling.utiles.Env
import com.plass.travling.utiles.TAG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private var retrofit: Retrofit? = null
    private var noTokenRetrofit: Retrofit? = null

    private val logInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, "Retrofit-Client : $message")

        when {
            message.isJsonObject() ->
                Log.d(TAG, JSONObject(message).toString(4))

            message.isJsonArray() ->
                Log.d(TAG, JSONObject(message).toString(4))

            else -> {
                try {
                    Log.d(TAG, JSONObject(message).toString(4))
                } catch (e: Exception) {
                }
            }
        }
    }.setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            val okhttpBuilder = OkHttpClient().newBuilder()
                .addInterceptor(logInterceptor)
                .addInterceptor(TokenInterceptor())
            retrofit = Retrofit.Builder()
                .baseUrl(Env.BASE_URl)
                .client(okhttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
        }
        return retrofit!!
    }

    private fun getNoTokenRetrofit(): Retrofit {
        if (noTokenRetrofit == null) {
            val okhttpBuilder = OkHttpClient().newBuilder()
                .addInterceptor(logInterceptor)
            noTokenRetrofit = Retrofit.Builder()
                .baseUrl(Env.BASE_URl)
                .client(okhttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
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

    fun getAI(): AIAPI = getRetrofit().create(AIAPI::class.java)
}