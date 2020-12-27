package com.simplx.apps.skepchatapp

data class Message(
    var id: String? = "",
    val text: String? = "",
    val photoUrl: String? = "",
    val imageUrl: String? = ""
)