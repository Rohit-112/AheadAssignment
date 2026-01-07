package com.ahead.assingment.network.api

import com.ahead.assingment.network.model.NavigationResponse
import retrofit2.http.GET

interface ApiService {

    @GET(ApiConstants.NAVIGATION_ENDPOINT)
    suspend fun getNavigation(): NavigationResponse
}
