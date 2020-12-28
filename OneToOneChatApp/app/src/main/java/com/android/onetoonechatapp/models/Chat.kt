package com.android.onetoonechatapp.models

data class Chat(
    val sender: String? = "",
    val receiver: String? = "",
    val senderUid: String? = "",
    val receiverUid: String? = "",
    val message: String? = "",
    val timeStamp: Long? = 0
)