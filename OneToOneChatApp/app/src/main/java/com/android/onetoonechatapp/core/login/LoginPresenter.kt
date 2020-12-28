package com.android.onetoonechatapp.core.login

import android.app.Activity

class LoginPresenter(
    private val mLoginView: LoginContract.View,
    private val mLoginInteractor: LoginInteractor
) : LoginContract.Presenter, LoginContract.OnLoginListener {


    override fun login(activity: Activity?, email: String?, password: String?) {
        mLoginInteractor.performFirebaseLogin(activity, email, password)

    }

    override fun onSuccess(message: String?) {
        mLoginView.onLoginSuccess(message)
    }

    override fun onFailure(message: String?) {
        mLoginView.onLoginFailure(message)
    }
}