package com.android.onetoonechatapp.core.login

import android.app.Activity

interface LoginContract {
    interface View {
        fun onLoginSuccess(message: String?)
        fun onLoginFailure(message: String?)
    }

    interface Presenter {
        fun login(activity: Activity?, email: String?, password: String?)
    }

    interface Interactor {
        fun performFirebaseLogin(activity: Activity?, email: String?, password: String?)
    }

    interface OnLoginListener {
        fun onSuccess(message: String?)
        fun onFailure(message: String?)
    }
}