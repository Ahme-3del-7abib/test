package com.android.onetoonechatapp

import android.app.Application

class FirebaseChatMainApp : Application() {

    companion object {
        private var sIsChatActivityOpen = false


        fun isChatActivityOpen(): Boolean {
            return sIsChatActivityOpen
        }

        fun setChatActivityOpen(isChatActivityOpen: Boolean) {
            sIsChatActivityOpen = isChatActivityOpen
        }

    }

    override fun onCreate() {
        super.onCreate()
    }

}