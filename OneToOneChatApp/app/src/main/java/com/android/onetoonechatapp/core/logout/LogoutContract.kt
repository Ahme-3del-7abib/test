package com.android.onetoonechatapp.core.logout

interface LogoutContract {

    interface View {
        fun onLogoutSuccess(message: String?)
        fun onLogoutFailure(message: String?)
    }

    interface Presenter {
        fun logout()
    }

    interface Interactor {
        fun performFirebaseLogout()
    }

    interface OnLogoutListener {
        fun onSuccess(message: String?)
        fun onFailure(message: String?)
    }
}