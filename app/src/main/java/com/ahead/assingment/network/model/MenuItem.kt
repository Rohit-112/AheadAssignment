package com.ahead.assingment.network.model

import com.google.gson.annotations.SerializedName

data class MenuItem(
    val type: Int,
    val module: String?,
    val label: String,
    val icon: String?,
    val url: String?,
    @SerializedName("class")
    val clazz: String
)

data class NavigationResponse(
    val result: NavigationResult?,
    val session_id: String?
)

data class NavigationResult(
    val menus: List<MenuItem>,
    val notification_count: Int,
    val friend_req_count: Int,
    val message_count: Int,
    val loggedin_user_id: Int
)