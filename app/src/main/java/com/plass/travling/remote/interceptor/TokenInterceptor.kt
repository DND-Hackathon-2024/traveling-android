package com.plass.travling.remote.interceptor

import com.plass.travling.local.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val e = chain
            .request()
            .newBuilder()
            .header("Authorization", SharedPreferencesManager.get("token")!!)

        return chain.proceed(e.build())
    }
}