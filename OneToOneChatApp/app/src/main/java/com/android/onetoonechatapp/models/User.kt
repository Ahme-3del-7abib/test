package com.android.onetoonechatapp.models

data class User(
    val uid: String? = "",
    val email: String? = "",
    val firebaseToken: String? = "",
    val receiverUid: String? = ""
)