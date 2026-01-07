package com.ahead.assingment.repository

import com.ahead.assingment.network.api.ApiService

class NavigationRepository(
    private val api: ApiService
) {

    suspend fun getNavigation() = api.getNavigation()
}
