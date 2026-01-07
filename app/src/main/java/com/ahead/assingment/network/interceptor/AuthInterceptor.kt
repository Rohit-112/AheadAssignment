package com.ahead.assingment.network.interceptor

import com.ahead.assingment.network.api.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val originalUrl = original.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("restApi", ApiConstants.REST_API)
            .addQueryParameter("sesapi_platform", ApiConstants.PLATFORM.toString())
            .addQueryParameter("auth_token", ApiConstants.AUTH_TOKEN)
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
