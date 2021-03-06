package com.android.onetoonechatapp.core.users.all

import com.android.onetoonechatapp.models.User


interface GetUsersContract {

    interface View {
        fun onGetAllUsersSuccess(users: ArrayList<User?>?)
        fun onGetAllUsersFailure(message: String?)
        fun onGetChatUsersSuccess(users: ArrayList<User?>?)
        fun onGetChatUsersFailure(message: String?)
    }

    interface Presenter {
        fun getAllUsers()
        fun getChatUsers()
    }

    interface Interactor {
        fun getAllUsersFromFirebase()
        fun getChatUsersFromFirebase()
    }

    interface OnGetAllUsersListener {
        fun onGetAllUsersSuccess(users: ArrayList<User?>?)
        fun onGetAllUsersFailure(message: String?)
    }

    interface OnGetChatUsersListener {
        fun onGetChatUsersSuccess(users: ArrayList<User?>?)
        fun onGetChatUsersFailure(message: String?)
    }
}