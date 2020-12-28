package com.android.onetoonechatapp.events

data class PushNotificationEvent(
    val title: String? = "",
    val message: String? = "",
    val username: String? = "",
    val uid: String? = "",
    val fcmToken: String? = ""
)